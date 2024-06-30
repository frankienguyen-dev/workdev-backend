package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.permission.*;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.Permission;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.PermissionRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.service.PermissionService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository permissionRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreatePermissionResponse> createNewPermission(
            CreatePermissionDto createPermissionDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Permission existingPermission = permissionRepository.findByName(
                createPermissionDto.getName()
        );
        if (existingPermission != null) {
            throw new ResourceExistingException("Permission", "name",
                    createPermissionDto.getName());
        }
        Permission newPermission = new Permission();
        newPermission.setName(createPermissionDto.getName());
        newPermission.setPath(createPermissionDto.getPath());
        newPermission.setMethod(createPermissionDto.getMethod());
        newPermission.setModule(createPermissionDto.getModule());
        newPermission.setCreatedBy(createdByUser);
        newPermission.setCreatedAt(LocalDateTime.now());
        Permission saveNewPermission = permissionRepository.save(newPermission);
        CreatePermissionResponse createPermissionResponse = new CreatePermissionResponse();
        createPermissionResponse.setId(saveNewPermission.getId());
        createPermissionResponse.setName(saveNewPermission.getName());
        createPermissionResponse.setPath(saveNewPermission.getPath());
        createPermissionResponse.setMethod(saveNewPermission.getMethod());
        createPermissionResponse.setModule(saveNewPermission.getModule());
        createPermissionResponse.setCreatedBy(getUserInfoFromToken);
        createPermissionResponse.setCreatedAt(saveNewPermission.getCreatedAt());
        return ApiResponse.success(
                "Create new permission successfully",
                HttpStatus.CREATED,
                createPermissionResponse
        );
    }

    @Override
    public ApiResponse<PermissionListResponse> getAllPermissions(int pageNo, int pageSize,
                                                                 String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Permission> permissions = permissionRepository.findAll(pageable);
        List<Permission> permissionContentList = permissions.getContent();
        List<PermissionResponse> permissionResponses = permissionContentList
                .stream().map(permission -> {
                    try {
                        return modelMapper.map(permission, PermissionResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(permissions.getNumber());
        metaData.setPageSize(permissions.getSize());
        metaData.setTotalElements(permissions.getTotalElements());
        metaData.setTotalPages(permissions.getTotalPages());
        metaData.setLastPage(permissions.isLast());
        PermissionListResponse permissionResponse = new PermissionListResponse();
        permissionResponse.setMeta(metaData);
        permissionResponse.setData(permissionResponses);
        return ApiResponse.success(
                "Get all permissions successfully",
                HttpStatus.OK,
                permissionResponse
        );
    }

    @Override
    public ApiResponse<PermissionResponse> getPermissionById(String id) {
        Permission findPermission = permissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Permission", "id", id)
        );
        PermissionResponse permissionInfo = modelMapper.map(findPermission, PermissionResponse.class);
        return ApiResponse.success(
                "Get permission by id successfully",
                HttpStatus.OK,
                permissionInfo
        );
    }

    @Override
    public ApiResponse<UpdatePermissionResponse> updatePermissionById(
            String id, UpdatePermissionDto updatePermissionDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Permission existingPermissionByName = permissionRepository.findByName(
                updatePermissionDto.getName()
        );
        Permission findPermission = permissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Permission", "id", id)
        );
        if (existingPermissionByName != null) {
            if (updatePermissionDto.getName().equalsIgnoreCase(existingPermissionByName
                    .getName()) && !existingPermissionByName.getId().equalsIgnoreCase(id)) {
                throw new ResourceExistingException("Permission", "name",
                        updatePermissionDto.getName());
            }
        }
        findPermission.setName(updatePermissionDto.getName());
        findPermission.setPath(updatePermissionDto.getPath());
        findPermission.setMethod(updatePermissionDto.getMethod());
        findPermission.setModule(updatePermissionDto.getModule());
        findPermission.setUpdatedBy(updatedByUser);
        findPermission.setUpdatedAt(LocalDateTime.now());
        Permission saveUpdatePermission = permissionRepository.save(findPermission);
        UpdatePermissionResponse updatePermissionResponse = new UpdatePermissionResponse();
        updatePermissionResponse.setId(saveUpdatePermission.getId());
        updatePermissionResponse.setName(saveUpdatePermission.getName());
        updatePermissionResponse.setPath(saveUpdatePermission.getPath());
        updatePermissionResponse.setMethod(saveUpdatePermission.getMethod());
        updatePermissionResponse.setModule(saveUpdatePermission.getModule());
        updatePermissionResponse.setUpdatedBy(getUserInfoFromToken);
        updatePermissionResponse.setUpdatedAt(saveUpdatePermission.getUpdatedAt());
        return ApiResponse.success(
                "Update permission by id successfully",
                HttpStatus.OK,
                updatePermissionResponse
        );
    }

    @Override
    public ApiResponse<DeletePermissionDto> softDeletePermissionById(String id) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Permission findPermission = permissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Permission", "id", id)
        );
        findPermission.setDeletedBy(deletedByUser);
        findPermission.setDeletedAt(LocalDateTime.now());
        findPermission.setIsDeleted(true);
        Permission saveDelete = permissionRepository.save(findPermission);
        DeletePermissionDto deletePermissionDtoResponse = new DeletePermissionDto();
        deletePermissionDtoResponse.setId(saveDelete.getId());
        deletePermissionDtoResponse.setDeletedBy(getUserInfoFromToken);
        deletePermissionDtoResponse.setDeletedAt(saveDelete.getDeletedAt());
        deletePermissionDtoResponse.setIsDeleted(saveDelete.getIsDeleted());
        return ApiResponse.success(
                "Delete permission by id successfully",
                HttpStatus.OK,
                deletePermissionDtoResponse
        );
    }

    @Override
    public ApiResponse<PermissionListResponse> searchPermission(String name, int pageNo, int pageSize,
                                                                String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Permission> permissions = permissionRepository.searchPermission(name, pageable);
        List<Permission> permissionContentList = permissions.getContent();
        List<PermissionResponse> permissionInfoList = permissionContentList.stream()
                .map(permission -> {
                    try {
                        return modelMapper.map(permission, PermissionResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(permissions.getNumber());
        metaData.setPageSize(permissions.getSize());
        metaData.setTotalElements(permissions.getTotalElements());
        metaData.setTotalPages(permissions.getTotalPages());
        metaData.setLastPage(permissions.isLast());
        PermissionListResponse permissionResponse = new PermissionListResponse();
        permissionResponse.setMeta(metaData);
        permissionResponse.setData(permissionInfoList);
        return ApiResponse.success(
                "Search Permission fetched successfully",
                HttpStatus.OK,
                permissionResponse
        );

    }
}
