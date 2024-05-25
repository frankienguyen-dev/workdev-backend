package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.user.*;
import com.frankie.workdev.service.UserService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private UserService userService;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserDto>> createNewUser(
            @RequestBody @Valid UserDto userDto) {
        ApiResponse<CreateUserDto> createUser = userService.createNewUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<UserResponse> getAllUsers = userService.getAllUsers(
                pageNo, pageSize, sortBy, sortDir
        );
        return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoDto>> getUserById(@PathVariable("id") String id) {
        ApiResponse<UserInfoDto> getUserById = userService.getUserById(id);
        return new ResponseEntity<>(getUserById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateUserDto>> updateUserById(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdateUserDto updateUserDto
    ) {
        ApiResponse<UpdateUserDto> updateUser = userService.updateUserById(id, updateUserDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteUserDto>> softDeleteUserById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteUserDto> softDeleteUser = userService.softDeleteUserById(id);
        return new ResponseEntity<>(softDeleteUser, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoDto>> getProfile() {
        ApiResponse<UserInfoDto> getProfile = userService.getProfile();
        return new ResponseEntity<>(getProfile, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserResponse>> searchUser(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<UserResponse> searchUser = userService.searchUser(email, pageNo, pageSize,
                sortBy, sortDir);
        return new ResponseEntity<>(searchUser, HttpStatus.OK);
    }
}
