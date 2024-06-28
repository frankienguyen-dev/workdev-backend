package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.permission.PermissionInfo;
import com.frankie.workdev.dto.permission.PermissionResponse;
import com.frankie.workdev.dto.role.request.CreateRoleDto;
import com.frankie.workdev.dto.role.request.DeleteRoleDto;
import com.frankie.workdev.dto.role.request.UpdateRoleDto;
import com.frankie.workdev.dto.role.response.CreateRoleResponse;
import com.frankie.workdev.dto.role.response.ListRoleResponse;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.role.response.UpdateRoleResponse;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.Permission;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.PermissionRepository;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.service.RoleService;
import com.frankie.workdev.util.UserInfoUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreateRoleResponse> createNewRole(CreateRoleDto createRoleDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role existingRoleName = roleRepository.findByName(createRoleDto.getName());
        if (existingRoleName != null) {
            throw new ResourceExistingException("Role", "name", createRoleDto.getName());
        }

        Role newRole = new Role();
        newRole.setName(createRoleDto.getName());
        newRole.setIsActive(createRoleDto.getIsActive());
        newRole.setCreatedBy(createdByUser);
        newRole.setCreatedAt(LocalDateTime.now());
        List<Permission> permissions = getListPermission(createRoleDto.getPermissions());
        newRole.setPermissions(permissions);
        Role saveNewRole = roleRepository.save(newRole);
        CreateRoleResponse createRoleResponse = new CreateRoleResponse();
        createRoleResponse.setId(saveNewRole.getId());
        createRoleResponse.setName(saveNewRole.getName());
        createRoleResponse.setIsActive(saveNewRole.getIsActive());
        createRoleResponse.setCreatedBy(getUserInfoFromToken);
        createRoleResponse.setCreatedAt(saveNewRole.getCreatedAt());
        List<PermissionResponse> permissionInfoList = permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionResponse.class))
                .collect(Collectors.toList());
        createRoleResponse.setPermissions(permissionInfoList);
        return ApiResponse.success(
                "Create new role successfully",
                HttpStatus.CREATED,
                createRoleResponse
        );
    }

    @Override
    public ApiResponse<ListRoleResponse> getAllRoles(int pageNo, int pageSize, String sortBy,
                                                     String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Role> roles = roleRepository.findAll(pageable);
        List<Role> roleContentList = roles.getContent();
        List<RoleResponse> roleDtoList = roleContentList.stream()
                .map(role -> {
                    try {
                        return modelMapper.map(role, RoleResponse.class);
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
        ListRoleResponse roleResponse = new ListRoleResponse();
        roleResponse.setMeta(metaData);
        roleResponse.setData(roleDtoList);
        return ApiResponse.success(
                "Get all roles successfully",
                HttpStatus.OK,
                roleResponse
        );
    }

    @Override
    public ApiResponse<RoleResponse> getRoleById(String id) {
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        RoleResponse getRole = modelMapper.map(findRole, RoleResponse.class);
        return ApiResponse.success(
                "Get role by id successfully",
                HttpStatus.OK,
                getRole
        );
    }

    @Override
    public ApiResponse<UpdateRoleResponse> updateRoleById(String id, UpdateRoleDto updateRoleDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role existingRoleName = roleRepository.findByName(updateRoleDto.getName());
        if (existingRoleName != null && !existingRoleName.getId().equals(id)) {
            throw new ResourceExistingException("Role", "name", updateRoleDto.getName());
        }
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        findRole.setName(updateRoleDto.getName());
        findRole.setIsActive(updateRoleDto.getIsActive());
        findRole.setUpdatedBy(updatedByUser);
        findRole.setUpdatedAt(LocalDateTime.now());
        List<Permission> permissions = getListPermission(updateRoleDto.getPermissions());
        findRole.setPermissions(permissions);
        Role saveUpdateRole = roleRepository.save(findRole);
        UpdateRoleResponse updateRoleDtoResponse = new UpdateRoleResponse();
        updateRoleDtoResponse.setId(saveUpdateRole.getId());
        updateRoleDtoResponse.setName(saveUpdateRole.getName());
        updateRoleDtoResponse.setIsActive(saveUpdateRole.getIsActive());
        updateRoleDtoResponse.setUpdatedBy(getUserInfoFromToken);
        updateRoleDtoResponse.setUpdatedAt(saveUpdateRole.getUpdatedAt());
        List<PermissionResponse> permissionInfoList = permissions.stream()
                .map(permission -> {
                    try {
                        return modelMapper.map(permission, PermissionResponse.class);
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
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Role findRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
        findRole.setDeletedBy(deletedByUser);
        findRole.setDeletedAt(LocalDateTime.now());
        findRole.setIsDeleted(true);
        Role saveDeleteRole = roleRepository.save(findRole);
        DeleteRoleDto deleteRoleDtoResponse = new DeleteRoleDto();
        deleteRoleDtoResponse.setId(saveDeleteRole.getId());
        deleteRoleDtoResponse.setDeletedBy(getUserInfoFromToken);
        deleteRoleDtoResponse.setDeletedAt(saveDeleteRole.getDeletedAt());
        deleteRoleDtoResponse.setIsDeleted(saveDeleteRole.getIsDeleted());
        return ApiResponse.success(
                "Delete role by id successfully",
                HttpStatus.OK,
                deleteRoleDtoResponse
        );
    }

    @Override
    public ApiResponse<ListRoleResponse> searchRole(String name, int pageNo, int pageSize,
                                                    String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Role> roles = roleRepository.searchRole(name, pageable);
        List<Role> roleListContent = roles.getContent();
        List<RoleResponse> roleDtoList = roleListContent.stream()
                .map(role -> {
                    try {
                        return modelMapper.map(role, RoleResponse.class);
                    }catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(roles.isLast());
        metaData.setPageNo(roles.getNumber());
        metaData.setTotalElements(roles.getTotalElements());
        metaData.setPageSize(roles.getSize());
        metaData.setTotalPages(roles.getTotalPages());
        ListRoleResponse roleResponse = new ListRoleResponse();
        roleResponse.setData(roleDtoList);
        roleResponse.setMeta(metaData);
        return ApiResponse.success(
                "Search Role fetched successfully",
                HttpStatus.OK,
                roleResponse
        );
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
