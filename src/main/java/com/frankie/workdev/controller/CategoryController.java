package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.category.*;
import com.frankie.workdev.service.CategoryService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCategoryDto>> createCategory(
            @RequestBody @Valid CreateCategoryDto createCategoryDto) {
        ApiResponse<CreateCategoryDto> create = categoryService.createCategory(createCategoryDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CategoryListResponse>> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<CategoryListResponse> getAllCategories = categoryService
                .getAllCategories(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable("id") String id) {
        ApiResponse<CategoryResponse> getCategoryById = categoryService.getCategoryById(id);
        return new ResponseEntity<>(getCategoryById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateCategoryDto>> updateCategoryById(
            @RequestBody @Valid UpdateCategoryDto updateCategoryDto, @PathVariable("id") String id) {
        ApiResponse<UpdateCategoryDto> updateCategoryById = categoryService
                .updateCategoryById(updateCategoryDto, id);
        return new ResponseEntity<>(updateCategoryById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteCategoryDto>> deleteCategoryById(@PathVariable("id") String id) {
        ApiResponse<DeleteCategoryDto> deleteCategory = categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<CategoryListResponse>> searchCategoryByName(
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
        ApiResponse<CategoryListResponse> searchCategory = categoryService
                .searchCategoryByName(name, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchCategory, HttpStatus.OK);
    }
}
