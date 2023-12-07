package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.user.*;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    @Override
    public ApiResponse<CreateUserDto> createNewUser(UserDto userDto) {
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
        User saveNewUser = userRepository.save(newUser);
        CreateUserDto createUserResponse = modelMapper
                .map(saveNewUser, CreateUserDto.class);
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
        User saveUpdated = userRepository.save(findUser);
        UpdateUserDto updateUser = modelMapper.map(saveUpdated, UpdateUserDto.class);
        return ApiResponse.success(
                "User updated successfully",
                HttpStatus.OK,
                updateUser
        );
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
}
