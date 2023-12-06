package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.user.*;

public interface UserService {
    ApiResponse<CreateUserDto> createNewUser(UserDto userDto);

    ApiResponse<UserResponse> getAllUsers(int pageNo, int pageSize,
                                          String sortBy, String sortDir);

    ApiResponse<UserInfoDto> getUserById(String id);

    ApiResponse<UpdateUserDto> updateUserById(String id, UpdateUserDto updateUserDto);

    ApiResponse<DeleteUserDto> softDeleteUserById(String id);
}
