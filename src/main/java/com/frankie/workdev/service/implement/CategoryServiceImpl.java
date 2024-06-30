package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.category.*;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.Category;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CategoryRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.CategoryService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<CreateCategoryDto> createCategory(CreateCategoryDto createCategoryDto) {
        JwtUserInfo getInfoUser = getInfoByToken();
        User createdByUser = userRepository.findByEmail(getInfoUser.getEmail());
        Category category = new Category();
        category.setId(createCategoryDto.getId());
        category.setName(createCategoryDto.getName());
        category.setDescription(createCategoryDto.getDescription());
        category.setCreatedBy(createdByUser);
        category.setCreatedAt(LocalDateTime.now());
        Category saved = categoryRepository.save(category);
        CreateCategoryDto response = modelMapper.map(saved, CreateCategoryDto.class);
        response.setCreatedBy(getInfoUser);
        response.setCreatedAt(saved.getCreatedAt());
        return ApiResponse.success(
                "Create category successfully",
                HttpStatus.CREATED,
                response
        );
    }

    @Override
    public ApiResponse<CategoryListResponse> getAllCategories(int pageNo, int pageSize,
                                                              String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<Category> categoryContentList = categories.getContent();
        List<CategoryResponse> categoryInfoList = categoryContentList.stream()
                .map(category -> {
                    try {
                        return modelMapper.map(category, CategoryResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(categories.isLast());
        metaData.setPageNo(categories.getNumber());
        metaData.setPageSize(categories.getSize());
        metaData.setTotalElements(categories.getTotalElements());
        metaData.setTotalPages(categories.getTotalPages());
        CategoryListResponse categoryResponse = new CategoryListResponse();
        categoryResponse.setMeta(metaData);
        categoryResponse.setData(categoryInfoList);
        return ApiResponse.success(
                "Fetched all categories successfully",
                HttpStatus.OK,
                categoryResponse
        );
    }

    @Override
    public ApiResponse<CategoryResponse> getCategoryById(String id) {
        try {
            Category findCategory = categoryRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Category", "id", id)
            );
            CategoryResponse categoryInfo = modelMapper.map(findCategory, CategoryResponse.class);

            return ApiResponse.success(
                    "Category fetched successfully",
                    HttpStatus.OK,
                    categoryInfo
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<UpdateCategoryDto> updateCategoryById(UpdateCategoryDto updateCategoryDto, String id) {
        JwtUserInfo getInfoUser = getInfoByToken();
        User updatedByUser = userRepository.findByEmail(getInfoUser.getEmail());
        Category findCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );
        findCategory.setName(updateCategoryDto.getName());
        findCategory.setDescription(updateCategoryDto.getDescription());
        findCategory.setUpdatedAt(LocalDateTime.now());
        findCategory.setUpdatedBy(updatedByUser);
        Category saved = categoryRepository.save(findCategory);
        UpdateCategoryDto response = modelMapper.map(saved, UpdateCategoryDto.class);
        response.setUpdatedBy(getInfoUser);
        response.setUpdatedAt(saved.getUpdatedAt());
        return ApiResponse.success(
                "Update category successfully",
                HttpStatus.OK,
                response
        );
    }

    @Override
    public ApiResponse<DeleteCategoryDto> deleteCategoryById(String id) {
        JwtUserInfo getInfoUser = getInfoByToken();
        User deletedByUser = userRepository.findByEmail(getInfoUser.getEmail());
        Category findCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );
        findCategory.setIsDeleted(true);
        findCategory.setDeletedBy(deletedByUser);
        findCategory.setDeletedAt(LocalDateTime.now());
        Category saved = categoryRepository.save(findCategory);
        DeleteCategoryDto response = modelMapper.map(findCategory, DeleteCategoryDto.class);
        response.setDeletedAt(saved.getDeletedAt());
        response.setDeletedBy(getInfoUser);
        return ApiResponse.success(
                "Delete category successfully",
                HttpStatus.OK,
                response
        );
    }

    @Override
    public ApiResponse<CategoryListResponse> searchCategoryByName(String name, int pageNo,
                                                                  int pageSize, String sortBy,
                                                                  String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Category> categories = categoryRepository.searchCategoryByName(name, pageable);
        List<Category> categoryContentList = categories.getContent();
        List<CategoryResponse> categoryDtoList = categoryContentList.stream().map(
                category -> {
                    try {
                        return modelMapper.map(category, CategoryResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(categories.isLast());
        metaData.setPageNo(categories.getNumber());
        metaData.setTotalPages(categories.getTotalPages());
        metaData.setTotalElements(categories.getTotalElements());
        metaData.setPageSize(categories.getSize());
        CategoryListResponse categoryResponse = new CategoryListResponse();
        categoryResponse.setMeta(metaData);
        categoryResponse.setData(categoryDtoList);
        return ApiResponse.success(
                "Search category successfully",
                HttpStatus.OK,
                categoryResponse
        );
    }


    private JwtUserInfo getInfoByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setEmail(getUserEmail);
        getUserInfo.setId(getUserId);
        return getUserInfo;
    }
}
