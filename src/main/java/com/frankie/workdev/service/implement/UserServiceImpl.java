package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.user.*;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CompanyRepository companyRepository;

    @Override
    public ApiResponse<CreateUserDto> createNewUser(UserDto userDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Company company = companyRepository.findByName(userDto.getCompany().getName());
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new ResourceExistingException("User", "email", userDto.getEmail());
        }
        User newUser = new User();
        newUser.setId(userDto.getId());
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setAddress(userDto.getAddress());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setGender(userDto.getGender());
        newUser.setAge(userDto.getAge());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCreatedBy(createdByUser);
        newUser.setCompany(company);
        User saveNewUser = userRepository.save(newUser);
        CreateUserDto createUserResponse = modelMapper
                .map(saveNewUser, CreateUserDto.class);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setId(saveNewUser.getCompany().getId());
        companyInfo.setName(saveNewUser.getCompany().getName());
        createUserResponse.setCompany(companyInfo);
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
                .map(user -> {
                    try {
                        return modelMapper.map(user, UserInfoDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
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
        UserInfoDto userInfoDto = modelMapper.map(findUser, UserInfoDto.class);
        return ApiResponse.success(
                "User fetched successfully",
                HttpStatus.OK,
                userInfoDto
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
            findUser.setFullName(updateUserDto.getFullName());
            findUser.setEmail(updateUserDto.getEmail());
            findUser.setAddress(updateUserDto.getAddress());
            findUser.setPhoneNumber(updateUserDto.getPhoneNumber());
            findUser.setGender(updateUserDto.getGender());
            findUser.setAge(updateUserDto.getAge());
            findUser.setUpdatedAt(LocalDateTime.now());
            findUser.setUpdatedBy(updatedByUser);
            findUser.setCompany(company);
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
        User findUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        findUser.setDeletedAt(LocalDateTime.now());
        findUser.setDeleted(true);
        User saveDeleted = userRepository.save(findUser);
        DeleteUserDto deleteUser = modelMapper.map(saveDeleted, DeleteUserDto.class);
        return ApiResponse.success(
                "Deleted user successfully",
                HttpStatus.OK,
                deleteUser
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
}
