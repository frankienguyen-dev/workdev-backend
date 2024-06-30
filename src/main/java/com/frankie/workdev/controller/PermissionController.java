package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.permission.*;
import com.frankie.workdev.service.PermissionService;
import com.frankie.workdev.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD operations for permission",
        description = "CRUD operations for permission controller: Create new permission, " +
                "Get all permissions, Get permission by id, Update permission by id, " +
                "Soft delete permission by id, Search permission by name"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/permissions")
public class PermissionController {

    private PermissionService permissionService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create new permission",
            description = "Create new permission controller is used to create new permission in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Permission created successfully"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePermissionResponse>> createNewPermission(
            @RequestBody @Valid CreatePermissionDto createPermissionDto) {
        ApiResponse<CreatePermissionResponse> createNewPermission = permissionService
                .createNewPermission(createPermissionDto);
        return new ResponseEntity<>(createNewPermission, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all permissions",
            description = "Get all permissions controller is used to get all permissions in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all permissions successfully"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PermissionListResponse>> getAllPermissions(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<PermissionListResponse> getAllPermissions = permissionService
                .getAllPermissions(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllPermissions, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get permission by id",
            description = "Get permission by id controller is used to get permission by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get permission by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(
            @PathVariable("id") String id) {
        ApiResponse<PermissionResponse> getPermissionById = permissionService.getPermissionById(id);
        return new ResponseEntity<>(getPermissionById, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update permission by id",
            description = "Update permission by id controller is used to update permission by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Update permission by id successfully"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdatePermissionResponse>> updatePermissionById(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdatePermissionDto updatePermissionDto) {
        ApiResponse<UpdatePermissionResponse> updatePermissionById = permissionService
                .updatePermissionById(id, updatePermissionDto);
        return new ResponseEntity<>(updatePermissionById, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Soft delete permission by id",
            description = "Soft delete permission by id controller is used to soft delete permission by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Soft delete permission by id successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeletePermissionDto>> deletePermissionById(
            @PathVariable("id") String id) {
        ApiResponse<DeletePermissionDto> deletePermission = permissionService
                .softDeletePermissionById(id);
        return new ResponseEntity<>(deletePermission, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Search permission by name",
            description = "Search permission by name controller is used to search permission by name in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Search permission by name successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PermissionListResponse>> searchPermission(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<PermissionListResponse> searchPermission = permissionService
                .searchPermission(name, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchPermission, HttpStatus.OK);
    }
}
