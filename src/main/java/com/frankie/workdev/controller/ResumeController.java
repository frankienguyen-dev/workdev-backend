package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.resume.*;
import com.frankie.workdev.service.ResumeService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/resumes")
public class ResumeController {
    private ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResumeResponse>> createResume(
            @RequestBody @Valid CreateResumeDto createResumeDto) {
        ApiResponse<CreateResumeResponse> createReumse = resumeService.createResume(createResumeDto);
        return new ResponseEntity<>(createReumse, HttpStatus.CREATED);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResumeResponse>> getResumeById(@PathVariable("id") String id) {
        ApiResponse<ResumeResponse> getResumeById = resumeService.getResumeById(id);
        return new ResponseEntity<>(getResumeById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateResumeResponse>> updateResumeById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateResumeDto updateResumeDto) {
        ApiResponse<UpdateResumeResponse> updateResume = resumeService.updateResumeById(id, updateResumeDto);
        return new ResponseEntity<>(updateResume, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteResumeDto>> deleteResumeById(@PathVariable("id") String id) {
        ApiResponse<DeleteResumeDto> deleteResume = resumeService.deleteResumeById(id);
        return new ResponseEntity<>(deleteResume, HttpStatus.OK);
    }

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
