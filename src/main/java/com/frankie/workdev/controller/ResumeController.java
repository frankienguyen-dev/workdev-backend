package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.resume.*;
import com.frankie.workdev.service.ResumeService;
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
        name = "CRUD operations for Resumes",
        description = "CRUD operations for resumes controller: Create Resume, Get All Resumes, " +
                "Get Resume By Id, Update Resume, Soft Delete Resume, Get Resume By Email" +
                "Get Resume By Job Id, Get Resume By User"
)
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/resumes")
public class ResumeController {
    private ResumeService resumeService;


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create new resume",
            description = "Create new resume controller is used to create new resume in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Resume created successfully"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreateResumeResponse>> createResume(
            @RequestBody @Valid CreateResumeDto createResumeDto) {
        ApiResponse<CreateResumeResponse> createReumse = resumeService.createResume(createResumeDto);
        return new ResponseEntity<>(createReumse, HttpStatus.CREATED);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all resumes",
            description = "Get all resumes controller is used to get all resumes in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all resumes successfully"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<ResumeListResponse>> getAllResumes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ResumeListResponse> getAllResumes = resumeService
                .getAllResumes(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllResumes, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get resume by id",
            description = "Get resume by id controller is used to get resume by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get resume by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResumeResponse>> getResumeById(@PathVariable("id") String id) {
        ApiResponse<ResumeResponse> getResumeById = resumeService.getResumeById(id);
        return new ResponseEntity<>(getResumeById, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update resume by id",
            description = "Update resume by id controller is used to update resume by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Update resume by id successfully"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateResumeResponse>> updateResumeById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateResumeDto updateResumeDto) {
        ApiResponse<UpdateResumeResponse> updateResume = resumeService.updateResumeById(id, updateResumeDto);
        return new ResponseEntity<>(updateResume, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Delete resume by id",
            description = "Delete resume by id controller is used to delete resume by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Delete resume by id successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteResumeDto>> deleteResumeById(@PathVariable("id") String id) {
        ApiResponse<DeleteResumeDto> deleteResume = resumeService.deleteResumeById(id);
        return new ResponseEntity<>(deleteResume, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get resume by user",
            description = "Get resume by user controller is used to get resume by user in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get resume by user successfully"
    )
    @GetMapping("/my-resumes")
    public ResponseEntity<ApiResponse<ResumeListResponse>> getResumeByUser(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ResumeListResponse> getResumeByUser = resumeService
                .getResumeByUser(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getResumeByUser, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Search resume by email",
            description = "Search resume by email controller is used to search resume by email in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Search resume by email successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResumeListResponse>> searchResumeByEmail(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ResumeListResponse> searchResume = resumeService
                .searchResumeByEmail(email, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchResume, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all resumes by job id",
            description = "Get all resumes by job id controller is used to get all resumes by job id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all resumes by job id successfully"
    )
    @GetMapping("/all-resumes/{id}")
    public ResponseEntity<ApiResponse<ResumeListResponse>> getAllResumeByJobId(
            @PathVariable("id") String id,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false)
            String sortDir
    ) {
        ApiResponse<ResumeListResponse> getAllResumeByJobId = resumeService
                .getAllResumesByJobId(id, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllResumeByJobId, HttpStatus.OK);
    }
}
