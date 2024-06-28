package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.role.request.CreateRoleDto;
import com.frankie.workdev.dto.role.request.DeleteRoleDto;
import com.frankie.workdev.dto.role.request.UpdateRoleDto;
import com.frankie.workdev.dto.role.response.CreateRoleResponse;
import com.frankie.workdev.dto.role.response.ListRoleResponse;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.role.response.UpdateRoleResponse;

public interface RoleService {
    ApiResponse<CreateRoleResponse> createNewRole(CreateRoleDto createRoleDto);

    ApiResponse<ListRoleResponse> getAllRoles(int pageNo, int pageSize, String sortBy,
                                              String sortDir);

    ApiResponse<RoleResponse> getRoleById(String id);

    ApiResponse<UpdateRoleResponse> updateRoleById(String id, UpdateRoleDto updateRoleDto);

    ApiResponse<DeleteRoleDto> deleteRoleById(String id);

    ApiResponse<ListRoleResponse> searchRole(String name, int pageNo, int pageSize,
                                             String sortBy, String sortDir);
}
