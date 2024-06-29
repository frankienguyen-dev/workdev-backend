package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.user.request.DeleteUserDto;
import com.frankie.workdev.dto.user.request.UpdateUserDto;
import com.frankie.workdev.dto.user.request.UserDto;
import com.frankie.workdev.dto.user.response.CreateUserResponse;
import com.frankie.workdev.dto.user.response.UpdateUserResponse;
import com.frankie.workdev.dto.user.response.UserInfoResponse;
import com.frankie.workdev.dto.user.response.UserListResponse;
import com.frankie.workdev.service.UserService;
import com.frankie.workdev.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD operations for user",
        description = "CRUD operations for user controller: Create new user, " +
                "Get all users, Get user by id, Update user by id, " +
                "Soft delete user by id, Get profile, Search user.")
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private UserService userService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create new user",
            description = "Create new user controller is used to create new user in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "User created successfully"
    )
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserResponse>> createNewUser(@RequestBody @Valid UserDto userDto) {
        ApiResponse<CreateUserResponse> createUser = userService.createNewUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all users",
            description = "Get all users controller is used to get all users in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all users successfully"
    )
//    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @GetMapping
    public ResponseEntity<ApiResponse<UserListResponse>> getAllUsers(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        ApiResponse<UserListResponse> getAllUsers = userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get user by id",
            description = "Get user by id controller is used to get user by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get user by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserById(@PathVariable("id") String id) {
        ApiResponse<UserInfoResponse> getUserById = userService.getUserById(id);
        return new ResponseEntity<>(getUserById, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update user by id",
            description = "Update user by id controller is used to update user by id in the database. "
    )

    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Update user by id successfully"
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUserById(@PathVariable("id") String id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        ApiResponse<UpdateUserResponse> updateUser = userService.updateUserById(id, updateUserDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Soft delete user by id",
            description = "Soft delete user by id controller is used to soft delete" +
                    " user by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Soft delete user by id successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteUserDto>> softDeleteUserById(@PathVariable("id") String id) {
        ApiResponse<DeleteUserDto> softDeleteUser = userService.softDeleteUserById(id);
        return new ResponseEntity<>(softDeleteUser, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get profile",
            description = "Get profile controller is used to get profile in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get profile successfully"
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getProfile() {
        ApiResponse<UserInfoResponse> getProfile = userService.getProfile();
        return new ResponseEntity<>(getProfile, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Search user by email with pagination",
            description = "Search user controller is used to search user in the database by email. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Search user successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserListResponse>> searchUser(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        ApiResponse<UserListResponse> searchUser = userService.searchUser(email, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchUser, HttpStatus.OK);
    }
}
