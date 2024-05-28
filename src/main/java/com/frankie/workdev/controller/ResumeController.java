package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.resume.*;
import com.frankie.workdev.service.ResumeService;
import com.frankie.workdev.util.AppConstants;
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
    public ResponseEntity<ApiResponse<CreateResumeDto>> createResume(
            @RequestBody CreateResumeDto createResumeDto) {
        ApiResponse<CreateResumeDto> createReumse = resumeService.createResume(createResumeDto);
        return new ResponseEntity<>(createReumse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResumeResponse>> getAllResumes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ResumeResponse> getAllResumes = resumeService
                .getAllResumes(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllResumes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResumeInfoDto>> getResumeById(@PathVariable("id") String id) {
        ApiResponse<ResumeInfoDto> getResumeById = resumeService.getResumeById(id);
        return new ResponseEntity<>(getResumeById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateResumeDto>> updateResumeById(
            @PathVariable("id") String id, @RequestBody UpdateResumeDto updateResumeDto) {
        ApiResponse<UpdateResumeDto> updateResume = resumeService.updateResumeById(id, updateResumeDto);
        return new ResponseEntity<>(updateResume, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteResumeDto>> deleteResumeById(@PathVariable("id") String id) {
        ApiResponse<DeleteResumeDto> deleteResume = resumeService.deleteResumeById(id);
        return new ResponseEntity<>(deleteResume, HttpStatus.OK);
    }

    @GetMapping("/my-resumes")
    public ResponseEntity<ApiResponse<ResumeResponse>> getResumeByUser(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<ResumeResponse> getResumeByUser = resumeService
                .getResumeByUser(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getResumeByUser, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResumeResponse>> searchResumeByEmail(
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
        ApiResponse<ResumeResponse> searchResume = resumeService
                .searchResumeByEmail(email, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchResume, HttpStatus.OK);
    }
}
