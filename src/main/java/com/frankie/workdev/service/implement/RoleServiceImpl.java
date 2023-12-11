package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.permission.PermissionInfo;
import com.frankie.workdev.dto.role.*;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.Permission;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.PermissionRepository;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<CreateRoleDto> createNewRole(CreateRoleDto createRoleDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role existingRoleName = roleRepository.findByName(createRoleDto.getName());
        if (existingRoleName != null) {
            throw new ResourceExistingException("Role", "name", createRoleDto.getName());
        }

        Role newRole = new Role();
        newRole.setName(createRoleDto.getName());
        newRole.setActive(true);
        newRole.setCreatedBy(createdByUser);
        newRole.setCreatedAt(LocalDateTime.now());
        List<Permission> permissions = getListPermission(createRoleDto.getPermissions());
        newRole.setPermissions(permissions);
        Role saveNewRole = roleRepository.save(newRole);
        CreateRoleDto createRoleResponse = new CreateRoleDto();
        createRoleResponse.setId(saveNewRole.getId());
        createRoleResponse.setName(saveNewRole.getName());
        createRoleResponse.setActive(saveNewRole.isActive());
        createRoleResponse.setCreatedBy(getUserInfoFromToken);
        createRoleResponse.setCreatedAt(saveNewRole.getCreatedAt());
        List<PermissionInfo> permissionInfoList = permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionInfo.class))
                .collect(Collectors.toList());
        createRoleResponse.setPermissions(permissionInfoList);
        return ApiResponse.success(
                "Create new role successfully",
                HttpStatus.CREATED,
                createRoleResponse
        );
    }

    @Override
    public ApiResponse<RoleResponse> getAllRoles(int pageNo, int pageSize, String sortBy,
                                                 String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Role> roles = roleRepository.findAll(pageable);
        List<Role> roleContentList = roles.getContent();
        List<RoleDto> roleDtoList = roleContentList.stream()
                .map(role -> {
                    try {
                        return modelMapper.map(role, RoleDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(roles.isLast());
        metaData.setTotalElements(roles.getTotalElements());
        metaData.setTotalPages(roles.getTotalPages());
        metaData.setPageSize(roles.getSize());
        metaData.setPageNo(roles.getNumber());
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setMeta(metaData);
        roleResponse.setData(roleDtoList);
        return ApiResponse.success(
                "Get all roles successfully",
                HttpStatus.OK,
                roleResponse
        );
    }

    @Override
    public ApiResponse<RoleDto> getRoleById(String id) {
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        RoleDto getRole = modelMapper.map(findRole, RoleDto.class);
        return ApiResponse.success(
                "Get role by id successfully",
                HttpStatus.OK,
                getRole
        );
    }

    @Override
    public ApiResponse<UpdateRoleDto> updateRoleById(String id, UpdateRoleDto updateRoleDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role existingRoleName = roleRepository.findByName(updateRoleDto.getName());
        if (existingRoleName != null && !existingRoleName.getId().equals(id)) {
            throw new ResourceExistingException("Role", "name", updateRoleDto.getName());
        }
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        findRole.setName(updateRoleDto.getName());
        findRole.setActive(updateRoleDto.isActive());
        findRole.setUpdatedBy(updatedByUser);
        findRole.setUpdatedAt(LocalDateTime.now());
        List<Permission> permissions = getListPermission(updateRoleDto.getPermissions());
        findRole.setPermissions(permissions);
        Role saveUpdateRole = roleRepository.save(findRole);
        UpdateRoleDto updateRoleDtoResponse = new UpdateRoleDto();
        updateRoleDtoResponse.setId(saveUpdateRole.getId());
        updateRoleDtoResponse.setName(saveUpdateRole.getName());
        updateRoleDtoResponse.setActive(saveUpdateRole.isActive());
        updateRoleDtoResponse.setUpdatedBy(getUserInfoFromToken);
        updateRoleDtoResponse.setUpdatedAt(saveUpdateRole.getUpdatedAt());
        List<PermissionInfo> permissionInfoList = permissions.stream()
                .map(permission -> {
                    try {
                        return modelMapper.map(permission, PermissionInfo.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        updateRoleDtoResponse.setPermissions(permissionInfoList);
        return ApiResponse.success(
                "Update role by id successfully",
                HttpStatus.OK,
                updateRoleDtoResponse
        );
    }

    @Override
    public ApiResponse<DeleteRoleDto> deleteRoleById(String id) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        findRole.setDeletedBy(deletedByUser);
        findRole.setDeletedAt(LocalDateTime.now());
        findRole.setDeleted(true);
        Role saveDeleteRole = roleRepository.save(findRole);
        DeleteRoleDto deleteRoleDtoResponse = new DeleteRoleDto();
        deleteRoleDtoResponse.setId(saveDeleteRole.getId());
        deleteRoleDtoResponse.setDeletedBy(getUserInfoFromToken);
        deleteRoleDtoResponse.setDeletedAt(saveDeleteRole.getDeletedAt());
        deleteRoleDtoResponse.setDeleted(saveDeleteRole.isDeleted());
        return ApiResponse.success(
                "Delete role by id successfully",
                HttpStatus.OK,
                deleteRoleDtoResponse
        );
    }


    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }

    private List<Permission> getListPermission(List<PermissionInfo> permissionInfos) {
        List<Permission> permissions = new ArrayList<>();
        for (PermissionInfo permissionInfo : permissionInfos) {
            Permission permission = permissionRepository.findByName(permissionInfo.getName());
            if (permission != null) {
                permissions.add(permission);
            } else {
                throw new ResourceNotFoundException("Permission", "name", permissionInfo.getName());
            }
        }
        return permissions;
    }


}
