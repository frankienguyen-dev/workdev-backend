package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.role.request.CreateRoleDto;
import com.frankie.workdev.dto.role.request.DeleteRoleDto;
import com.frankie.workdev.dto.role.request.UpdateRoleDto;
import com.frankie.workdev.dto.role.response.CreateRoleResponse;
import com.frankie.workdev.dto.role.response.ListRoleResponse;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.role.response.UpdateRoleResponse;
import com.frankie.workdev.service.RoleService;
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
        name = "CRUD operations for role",
        description = "CRUD operations for role controller: Create new role, " +
                "Get all roles, Get role by id, Update role by id, Delete role by id, Search role"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/roles")
public class RoleController {
    private RoleService roleService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create new role",
            description = "Create new role controller is used to create new role in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Role created successfully"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreateRoleResponse>> createNewRole(
            @RequestBody @Valid CreateRoleDto createRoleDto) {
        ApiResponse<CreateRoleResponse> createRole = roleService.createNewRole(createRoleDto);
        return new ResponseEntity<>(createRole, HttpStatus.CREATED);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all roles",
            description = "Get all roles controller is used to get all roles in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all roles successfully"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<ListRoleResponse>> getAllRoles(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ListRoleResponse> getAllRoles = roleService.getAllRoles(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllRoles, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get role by id",
            description = "Get role by id controller is used to get role by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get role by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable("id") String id) {
        ApiResponse<RoleResponse> getRoleById = roleService.getRoleById(id);
        return new ResponseEntity<>(getRoleById, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update role by id",
            description = "Update role by id controller is used to update role by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Update role by id successfully"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateRoleResponse>> updateRoleById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateRoleDto updateRoleDto) {
        ApiResponse<UpdateRoleResponse> updateRole = roleService.updateRoleById(id, updateRoleDto);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Delete role by id",
            description = "Delete role by id controller is used to delete role by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Delete role by id successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteRoleDto>> deleteRoleById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteRoleDto> deletedRole = roleService.deleteRoleById(id);
        return new ResponseEntity<>(deletedRole, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Search role by name",
            description = "Search role by name controller is used to search role by name in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Search role by name successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ListRoleResponse>> searchRole(
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
        ApiResponse<ListRoleResponse> searchRole = roleService
                .searchRole(name, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchRole, HttpStatus.OK);
    }
}
