package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.permission.*;
import com.frankie.workdev.service.PermissionService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/permissions")
public class PermissionController {

    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePermissionDto>> createNewPermission(
            @RequestBody @Valid CreatePermissionDto createPermissionDto) {
        ApiResponse<CreatePermissionDto> createNewPermission = permissionService
                .createNewPermission(createPermissionDto);
        return new ResponseEntity<>(createNewPermission, HttpStatus.CREATED);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionInfo>> getPermissionById(
            @PathVariable("id") String id) {
        ApiResponse<PermissionInfo> getPermissionById = permissionService.getPermissionById(id);
        return new ResponseEntity<>(getPermissionById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdatePermissionDto>> updatePermissionById(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdatePermissionDto updatePermissionDto) {
        ApiResponse<UpdatePermissionDto> updatePermissionById = permissionService
                .updatePermissionById(id, updatePermissionDto);
        return new ResponseEntity<>(updatePermissionById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeletePermissionDto>> deletePermissionById(
            @PathVariable("id") String id) {
        ApiResponse<DeletePermissionDto> deletePermission = permissionService
                .softDeletePermissionById(id);
        return new ResponseEntity<>(deletePermission, HttpStatus.OK);
    }

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
