package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.user.request.DeleteUserDto;
import com.frankie.workdev.dto.user.request.UpdateUserDto;
import com.frankie.workdev.dto.user.request.UserDto;
import com.frankie.workdev.dto.user.response.CreateUserResponse;
import com.frankie.workdev.dto.user.response.UpdateUserResponse;
import com.frankie.workdev.dto.user.response.UserInfoResponse;
import com.frankie.workdev.dto.user.response.UserListResponse;

public interface UserService {
    ApiResponse<CreateUserResponse> createNewUser(UserDto userDto);

    ApiResponse<UserListResponse> getAllUsers(int pageNo, int pageSize,
                                              String sortBy, String sortDir);

    ApiResponse<UserInfoResponse> getUserById(String id);

    ApiResponse<UpdateUserResponse> updateUserById(String id, UpdateUserDto updateUserDto);

    ApiResponse<DeleteUserDto> softDeleteUserById(String id);

    ApiResponse<UserInfoResponse> getProfile();

    ApiResponse<UserListResponse> searchUser(String email, int pageNo, int pageSize,
                                             String sortBy, String sortDir);
}
