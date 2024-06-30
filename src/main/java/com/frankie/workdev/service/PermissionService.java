package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.permission.*;

public interface PermissionService {
    ApiResponse<CreatePermissionResponse> createNewPermission(CreatePermissionDto createPermissionDto);

    ApiResponse<PermissionListResponse> getAllPermissions(int pageNo, int pageSize,
                                                          String sortBy, String sortDir);

    ApiResponse<PermissionResponse> getPermissionById(String id);

    ApiResponse<UpdatePermissionResponse> updatePermissionById(
            String id, UpdatePermissionDto updatePermissionDto);

    ApiResponse<DeletePermissionDto> softDeletePermissionById(String id);

    ApiResponse<PermissionListResponse> searchPermission(String name, int pageNo, int pageSize,
                                                         String sortBy, String sortDir);
}
