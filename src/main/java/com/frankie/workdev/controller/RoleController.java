package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.role.*;
import com.frankie.workdev.service.RoleService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/roles")
public class RoleController {

    private RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateRoleDto>> createNewRole(
            @RequestBody @Valid CreateRoleDto createRoleDto) {
        ApiResponse<CreateRoleDto> createRole = roleService.createNewRole(createRoleDto);
        return new ResponseEntity<>(createRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<RoleResponse>> getAllRoles(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<RoleResponse> getAllRoles = roleService.getAllRoles(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllRoles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleById(@PathVariable("id") String id) {
        ApiResponse<RoleDto> getRoleById = roleService.getRoleById(id);
        return new ResponseEntity<>(getRoleById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateRoleDto>> updateRoleById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateRoleDto updateRoleDto) {
        ApiResponse<UpdateRoleDto> updateRole = roleService.updateRoleById(id, updateRoleDto);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteRoleDto>> deleteRoleById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteRoleDto> deletedRole = roleService.deleteRoleById(id);
        return new ResponseEntity<>(deletedRole, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<RoleResponse>> searchRole(
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<RoleResponse> searchRole = roleService
                .searchRole(name, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchRole, HttpStatus.OK);
    }
}
