package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.permission.*;

public interface PermissionService {
    ApiResponse<CreatePermissionDto> createNewPermission(CreatePermissionDto createPermissionDto);

    ApiResponse<PermissionResponse> getAllPermissions(int pageNo, int pageSize,
                                                      String sortBy, String sortDir);

    ApiResponse<PermissionInfo> getPermissionById(String id);

    ApiResponse<UpdatePermissionDto> updatePermissionById(
            String id, UpdatePermissionDto updatePermissionDto);

    ApiResponse<DeletePermissionDto> softDeletePermissionById(String id);

    ApiResponse<PermissionResponse> searchPermission(String name, int pageNo, int pageSize,
                                                     String sortBy, String sortDir);
}
