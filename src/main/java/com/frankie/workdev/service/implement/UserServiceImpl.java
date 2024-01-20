package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.role.RoleDto;
import com.frankie.workdev.dto.user.*;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CompanyRepository companyRepository;
    private RoleRepository roleRepository;

    @Override
    public ApiResponse<CreateUserDto> createNewUser(UserDto userDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
//        Company company = companyRepository.findByName(userDto.getCompany().getName());
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new ResourceExistingException("User", "email", userDto.getEmail());
        }
        List<Role> roles = new ArrayList<>();
        for (RoleDto role : userDto.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole == null) {
                throw new ResourceNotFoundException("Role", "name", role.getName());
            } else {
                roles.add(existingRole);
            }
        }
        User newUser = new User();
        newUser.setId(userDto.getId());
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setAddress(userDto.getAddress());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setGender(userDto.getGender());
        newUser.setAge(userDto.getAge());
        newUser.setTitle(userDto.getTitle());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCreatedBy(createdByUser);
//        newUser.setCompany(company);
        newUser.setRoles(roles);
        User saveNewUser = userRepository.save(newUser);
        CreateUserDto createUserResponse = modelMapper
                .map(saveNewUser, CreateUserDto.class);
//        CompanyInfo companyInfo = new CompanyInfo();
//        companyInfo.setId(saveNewUser.getCompany().getId());
//        companyInfo.setName(saveNewUser.getCompany().getName());
//        createUserResponse.setCompany(companyInfo);
        List<RoleDto> roleDtoList = saveNewUser.getRoles().stream()
                .map(role -> {
                    try {
                        return modelMapper.map(role, RoleDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        createUserResponse.setRoles(roleDtoList);
        return ApiResponse.success(
                "User created successfully",
                HttpStatus.CREATED,
                createUserResponse
        );
    }

    @Override
    public ApiResponse<UserResponse> getAllUsers(int pageNo, int pageSize,
                                                 String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);
        List<User> userContentList = users.getContent();
        List<UserInfoDto> userList = userContentList.stream()
                .map(user ->mappingUser(user)).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(users.isLast());
        metaData.setTotalPages(users.getTotalPages());
        metaData.setTotalElements(users.getTotalElements());
        metaData.setPageNo(users.getNumber());
        metaData.setPageSize(users.getSize());
        UserResponse userResponse = new UserResponse();
        userResponse.setData(userList);
        userResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetched all users successfully",
                HttpStatus.OK,
                userResponse
        );

    }



    @Override
    public ApiResponse<UserInfoDto> getUserById(String id) {
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );



        return ApiResponse.success(
                "User fetched successfully",
                HttpStatus.OK,
                mappingUser(findUser)
        );

    }

    @Override
    public ApiResponse<UpdateUserDto> updateUserById(String id, UpdateUserDto updateUserDto) {
        try {
            JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
            User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            Company company = companyRepository.findByName(updateUserDto.getCompany().getName());
            User findUser = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", id)
            );
            String newEmail = updateUserDto.getEmail();
            if (newEmail != null && !newEmail.equals(findUser.getEmail())) {
                User existingUserEmail = userRepository.findByEmail(updateUserDto.getEmail());
                if (existingUserEmail != null) {
                    throw new ResourceExistingException("User", "email", updateUserDto.getEmail());
                }
            }
            List<Role> roleList = new ArrayList<>();
            for (RoleDto role : updateUserDto.getRoles()) {
                Role existingRole = roleRepository.findByName(role.getName());
                if (existingRole == null) {
                    throw new ResourceNotFoundException("Role", "name", role.getName());
                } else {
                    roleList.add(existingRole);
                }
            }
            findUser.setFullName(updateUserDto.getFullName());
            findUser.setEmail(updateUserDto.getEmail());
            findUser.setAddress(updateUserDto.getAddress());
            findUser.setPhoneNumber(updateUserDto.getPhoneNumber());
            findUser.setGender(updateUserDto.getGender());
            findUser.setAge(updateUserDto.getAge());
            findUser.setTitle(updateUserDto.getTitle());
            findUser.setUpdatedAt(LocalDateTime.now());
            findUser.setUpdatedBy(updatedByUser);
            findUser.setCompany(company);
            findUser.setRoles(roleList);
            User saveUpdated = userRepository.save(findUser);
            UpdateUserDto updateUser = new UpdateUserDto();
            updateUser.setId(saveUpdated.getId());
            updateUser.setFullName(saveUpdated.getFullName());
            updateUser.setEmail(saveUpdated.getEmail());
            updateUser.setAddress(saveUpdated.getAddress());
            updateUser.setPhoneNumber(saveUpdated.getPhoneNumber());
            updateUser.setGender(saveUpdated.getGender());
            updateUser.setAge(saveUpdated.getAge());
            updateUser.setUpdatedAt(saveUpdated.getUpdatedAt());
            updateUser.setUpdatedBy(getUserInfoFromToken);
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setId(saveUpdated.getCompany().getId());
            companyInfo.setName(saveUpdated.getCompany().getName());
            updateUser.setCompany(companyInfo);
            List<RoleDto> roleDtoList = saveUpdated.getRoles().stream()
                    .map(role -> {
                        try {
                            return modelMapper.map(role, RoleDto.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                    }).collect(Collectors.toList());
            updateUser.setRoles(roleDtoList);
            return ApiResponse.success(
                    "User updated successfully",
                    HttpStatus.OK,
                    updateUser
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<DeleteUserDto> softDeleteUserById(String id) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        findUser.setDeletedAt(LocalDateTime.now());
        findUser.setDeleted(true);
        findUser.setDeletedBy(updatedByUser);
        User saveDeleted = userRepository.save(findUser);
        DeleteUserDto deleteUser = modelMapper.map(saveDeleted, DeleteUserDto.class);
        return ApiResponse.success(
                "Deleted user successfully",
                HttpStatus.OK,
                deleteUser
        );
    }

    @Override
    public ApiResponse<UserInfoDto> getProfile() {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User findUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        UserInfoDto userInfoDto = mappingUser(findUser);
        return ApiResponse.success(
            "User fetched successfully",
            HttpStatus.OK,
            userInfoDto
        );
    }

    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        String getUserEmail = authentication.getName();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }

    private UserInfoDto mappingUser(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setFullName(user.getFullName());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setAddress(user.getAddress());
        userInfoDto.setPhoneNumber(user.getPhoneNumber());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setAge(user.getAge());
        userInfoDto.setCreatedAt(user.getCreatedAt());
        userInfoDto.setUpdatedAt(user.getUpdatedAt());
        userInfoDto.setDeletedAt(user.getDeletedAt());
        TypeMap<User, UserInfoDto> userTypeMap = modelMapper
                .typeMap(User.class, UserInfoDto.class)
                .addMappings(mapper -> {
                    mapper.map(User::getCreatedBy, UserInfoDto::setCreatedBy);
                    mapper.map(User::getUpdatedBy, UserInfoDto::setUpdatedBy);
                    mapper.map(User::getDeletedBy, UserInfoDto::setDeletedBy);
                    mapper.map(User::getCompany, UserInfoDto::setCompany);
                    mapper.map(User::getRoles, UserInfoDto::setRoles);
                });

        TypeMap<Role, RoleDto> roleTypeMap = modelMapper
                .typeMap(Role.class, RoleDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCreatedBy().getId(), RoleDto::setCreatedBy);
                    mapper.map(src -> src.getUpdatedBy().getId(), RoleDto::setUpdatedBy);
                    mapper.map(src -> src.getDeletedBy().getId(), RoleDto::setDeletedBy);
                    mapper.map(Role::getPermissions, RoleDto::setPermissions);
                });

        if (user.getRoles() != null) {
            userTypeMap.addMappings(mapper -> mapper.map(
                    src -> user.getRoles().stream().map(role -> roleTypeMap.map(role))
                            .collect(Collectors.toList()), UserInfoDto::setRoles));
        }
        return userTypeMap.map(user);
    }
}
