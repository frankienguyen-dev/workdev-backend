package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.role.*;

public interface RoleService {
    ApiResponse<CreateRoleDto> createNewRole(CreateRoleDto createRoleDto);

    ApiResponse<RoleResponse> getAllRoles(int pageNo, int pageSize, String sortBy,
                                          String sortDir);

    ApiResponse<RoleDto> getRoleById(String id);

    ApiResponse<UpdateRoleDto> updateRoleById(String id, UpdateRoleDto updateRoleDto);

    ApiResponse<DeleteRoleDto> deleteRoleById(String id);

    ApiResponse<RoleResponse> searchRole(String name, int pageNo, int pageSize,
                                         String sortBy, String sortDir);
}
