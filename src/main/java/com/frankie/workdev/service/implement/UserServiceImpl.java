package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.dto.user.request.DeleteUserDto;
import com.frankie.workdev.dto.user.request.UpdateUserDto;
import com.frankie.workdev.dto.user.request.UserDto;
import com.frankie.workdev.dto.user.response.*;
import com.frankie.workdev.entity.*;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.FileRepository;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.service.UserService;
import com.frankie.workdev.util.UserInfoUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    private FileRepository fileRepository;
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreateUserResponse> createNewUser(UserDto userDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
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
        newUser.setBiography(userDto.getBiography());
        newUser.setCoverLetter(userDto.getCoverLetter());
        newUser.setCreatedBy(createdByUser);
        newUser.setEducation(userDto.getEducation());
        newUser.setExperience(userDto.getExperience());
        if (userDto.getCompany() != null && userDto.getCompany().getName() != null) {
            Company company = companyRepository.findByName(userDto.getCompany().getName());
            newUser.setCompany(company);
        } else {
            newUser.setCompany(null);
        }

        if (userDto.getAvatar() != null && userDto.getAvatar().getId() != null) {
            FileEntity avatar = fileRepository.findById(userDto.getAvatar().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("File", "id", userDto.getAvatar().getId())
            );
            newUser.setAvatar(avatar);
        } else {
            newUser.setAvatar(null);
        }

//        newUser.setCompany(company);
        newUser.setRoles(roles);
        User saveNewUser = userRepository.save(newUser);
        CreateUserResponse createUserResponse = modelMapper
                .map(saveNewUser, CreateUserResponse.class);
        if (saveNewUser.getCompany() != null) {
            CompanyInfoResponse companyInfo = new CompanyInfoResponse();
            companyInfo.setId(saveNewUser.getCompany().getId());
            companyInfo.setName(saveNewUser.getCompany().getName());
            createUserResponse.setCompany(companyInfo);
        } else {
            createUserResponse.setCompany(null);
        }

        List<RoleResponse> roleDtoList = saveNewUser.getRoles().stream()
                .map(role -> {
                    try {
                        return modelMapper.map(role, RoleResponse.class);
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
    public ApiResponse<UserListResponse> getAllUsers(int pageNo, int pageSize,
                                                     String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
            Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
            Page<User> users = userRepository.findAll(pageable);
            List<User> userContentList = users.getContent();
            List<UserInfoResponse> userList = userContentList.stream()
                    .map(this::mappingUser).collect(Collectors.toList());
            MetaData metaData = new MetaData();
            metaData.setLastPage(users.isLast());
            metaData.setTotalPages(users.getTotalPages());
            metaData.setTotalElements(users.getTotalElements());
            metaData.setPageNo(users.getNumber());
            metaData.setPageSize(users.getSize());
            UserListResponse userResponse = new UserListResponse();
            userResponse.setData(userList);
            userResponse.setMeta(metaData);
            return ApiResponse.success(
                    "Fetched all users successfully",
                    HttpStatus.OK,
                    userResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public ApiResponse<UserInfoResponse> getUserById(String id) {
        try {
            User findUser = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", id)
            );

            return ApiResponse.success(
                    "User fetched successfully",
                    HttpStatus.OK,
                    mappingUser(findUser)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public ApiResponse<UpdateUserResponse> updateUserById(String id, UpdateUserDto updateUserDto) {
        try {
            JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
            User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
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
            findUser.setExperience(updateUserDto.getExperience());
            findUser.setEducation(updateUserDto.getEducation());
            findUser.setCoverLetter(updateUserDto.getCoverLetter());
            findUser.setBiography(updateUserDto.getBiography());
            if (updateUserDto.getCompany() != null && updateUserDto.getCompany().getName() != null) {
                Company company = companyRepository.findByName(updateUserDto.getCompany().getName());
                findUser.setCompany(company);
            } else {
                findUser.setCompany(null);
            }
            findUser.setRoles(roleList);
            if (updateUserDto.getAvatar() != null && updateUserDto.getAvatar().getId() != null) {
                FileEntity avatar = fileRepository.findById(updateUserDto.getAvatar().getId()).orElseThrow(
                        () -> new ResourceNotFoundException("File", "id", updatedByUser.getAvatar().getId())
                );
                findUser.setAvatar(avatar);
            } else {
                findUser.setAvatar(null);
            }
            User saveUpdated = userRepository.save(findUser);
            UpdateUserResponse updateUser = new UpdateUserResponse();
            updateUser.setId(saveUpdated.getId());
            updateUser.setFullName(saveUpdated.getFullName());
            updateUser.setEmail(saveUpdated.getEmail());
            updateUser.setAddress(saveUpdated.getAddress());
            updateUser.setPhoneNumber(saveUpdated.getPhoneNumber());
            updateUser.setGender(saveUpdated.getGender());
            updateUser.setAge(saveUpdated.getAge());
            updateUser.setUpdatedAt(saveUpdated.getUpdatedAt());
            updateUser.setTitle(saveUpdated.getTitle());
            updateUser.setExperience(saveUpdated.getExperience());
            updateUser.setEducation(saveUpdated.getEducation());
            updateUser.setUpdatedBy(getUserInfoFromToken);
            if (saveUpdated.getAvatar() != null) {
                updateUser.setAvatar(modelMapper.map(saveUpdated.getAvatar(), UploadFileResponse.class));
            } else {
                updateUser.setAvatar(null);
            }
            if (saveUpdated.getCompany() != null) {
                CompanyInfoResponse companyInfo = new CompanyInfoResponse();
                companyInfo.setId(saveUpdated.getCompany().getId());
                companyInfo.setName(saveUpdated.getCompany().getName());
                updateUser.setCompany(companyInfo);
            } else {
                updateUser.setCompany(null);
            }
            List<RoleResponse> roleDtoList = saveUpdated.getRoles().stream()
                    .map(role -> {
                        try {
                            return modelMapper.map(role, RoleResponse.class);
                        } catch (Exception e) {
                            System.out.println("check e");
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
            System.out.println("cehck e catch");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<DeleteUserDto> softDeleteUserById(String id) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        findUser.setDeletedAt(LocalDateTime.now());
        findUser.setIsDeleted(true);
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
    public ApiResponse<UserInfoResponse> getProfile() {
        try {
            JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
            User findUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            UserInfoResponse userInfoDto = mappingUser(findUser);
//            userInfoDto.setAvatar(modelMapper.map(findUser.getAvatar(), FileUploadDto.class));
            return ApiResponse.success(
                    "User fetched successfully",
                    HttpStatus.OK,
                    userInfoDto
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<UserListResponse> searchUser(String email, int pageNo, int pageSize,
                                                    String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<User> users = userRepository.searchUser(email, pageable);
        List<User> listSearchUserContent = users.getContent();
        List<UserInfoResponse> userInfoDtoListSearch = listSearchUserContent.stream().map(
                user -> {
                    try {
                        return mappingUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageSize(users.getSize());
        metaData.setLastPage(users.isLast());
        metaData.setTotalElements(users.getTotalElements());
        metaData.setPageNo(users.getNumber());
        metaData.setTotalPages(users.getTotalPages());
        UserListResponse userResponse = new UserListResponse();
        userResponse.setData(userInfoDtoListSearch);
        userResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetched all users successfully",
                HttpStatus.OK,
                userResponse
        );
    }

    private UserInfoResponse mappingUser(User user) {

        UserInfoResponse userInfoDto = new UserInfoResponse();
        userInfoDto.setId(user.getId());
        userInfoDto.setFullName(user.getFullName());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setAddress(user.getAddress());
        userInfoDto.setPhoneNumber(user.getPhoneNumber());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setAge(user.getAge());
//        userInfoDto.setEducation(user.getEducation());
//        userInfoDto.setTitle(user.getTitle());
//        userInfoDto.setExperience(user.getExperience());
//        userInfoDto.setAvatar(modelMapper.map(user.getAvatar(), FileUploadDto.class));
        userInfoDto.setCreatedAt(user.getCreatedAt());
        userInfoDto.setUpdatedAt(user.getUpdatedAt());
        userInfoDto.setDeletedAt(user.getDeletedAt());

        TypeMap<User, UserInfoResponse> userTypeMap = modelMapper
                .typeMap(User.class, UserInfoResponse.class)
                .addMappings(mapper -> {
                    mapper.map(User::getCreatedBy, UserInfoResponse::setCreatedBy);
                    mapper.map(User::getUpdatedBy, UserInfoResponse::setUpdatedBy);
                    mapper.map(User::getDeletedBy, UserInfoResponse::setDeletedBy);
                    mapper.map(User::getCompany, UserInfoResponse::setCompany);
                    mapper.map(User::getRoles, UserInfoResponse::setRoles);
//                    mapper.map(User::getResumes, UserInfoDto::setResumes);
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
                            .collect(Collectors.toList()), UserInfoResponse::setRoles));
        }
        return userTypeMap.map(user);

    }

}
