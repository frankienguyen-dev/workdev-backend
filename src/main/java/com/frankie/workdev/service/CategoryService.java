package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.category.*;

public interface CategoryService {
    ApiResponse<CreateCategoryDto> createCategory(CreateCategoryDto createCategoryDto);

    ApiResponse<CategoryResponse> getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<CategoryInfo> getCategoryById(String id);

    ApiResponse<UpdateCategoryDto> updateCategoryById(UpdateCategoryDto updateCategoryDto, String id);

    ApiResponse<DeleteCategoryDto> deleteCategoryById(String id);
}