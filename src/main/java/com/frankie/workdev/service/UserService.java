package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.user.*;

import java.awt.print.Pageable;

public interface UserService {
    ApiResponse<CreateUserDto> createNewUser(UserDto userDto);

    ApiResponse<UserResponse> getAllUsers(int pageNo, int pageSize,
                                          String sortBy, String sortDir);

    ApiResponse<UserInfoDto> getUserById(String id);

    ApiResponse<UpdateUserDto> updateUserById(String id, UpdateUserDto updateUserDto);

    ApiResponse<DeleteUserDto> softDeleteUserById(String id);

    ApiResponse<UserInfoDto> getProfile();

    ApiResponse<UserResponse> searchUser(String email, int pageNo, int pageSize,
                                         String sortBy, String sortDir);
}
