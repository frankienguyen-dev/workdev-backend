package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.job.*;
import com.frankie.workdev.service.JobService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/jobs")
public class JobController {

    private JobService jobService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateJobResponse>> createJob(
            @RequestBody @Valid CreateJobDto createJobDto) {
        ApiResponse<CreateJobResponse> createJob = jobService.createJob(createJobDto);
        return new ResponseEntity<>(createJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<JobListResponse>> getAllJobs(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<JobListResponse> getAllJobs = jobService.getAllJobs(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllJobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getJobById(@PathVariable("id") String id) {
        ApiResponse<JobResponse> getJobById = jobService.getJobById(id);
        return new ResponseEntity<>(getJobById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateJobResponse>> updateJobById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateJobDto updateJobDto) {
        ApiResponse<UpdateJobResponse> updateJob = jobService.updateJobById(id, updateJobDto);
        return new ResponseEntity<>(updateJob, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteJobDto>> deletedJobById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteJobDto> deletedJob = jobService.deleteJobById(id);
        return new ResponseEntity<>(deletedJob, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<JobListResponse>> searchJob(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "salary", required = false) Long salary
//            @RequestParam(value = "companyName", required = false) String companyName
    ) {
        ApiResponse<JobListResponse> searchJob = jobService.searchJob(name, location, salary,
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchJob, HttpStatus.OK);
    }

    @PostMapping("/favorite/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> favoriteJob(@PathVariable("id") String id) {
        ApiResponse<JobResponse> favoriteJob = jobService.favoriteJob(id);
        return new ResponseEntity<>(favoriteJob, HttpStatus.OK);
    }

    @GetMapping("/my-favorite-job")
    public ResponseEntity<ApiResponse<JobListResponse>> getFavoriteJobs(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<JobListResponse> getFavoriteJobs = jobService.getFavoriteJobs(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getFavoriteJobs, HttpStatus.OK);
    }

    @GetMapping("/my-list-job")
    public ResponseEntity<ApiResponse<JobListResponse>> getJobListByUser(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<JobListResponse> myJobList = jobService.getJobListByUser(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(myJobList, HttpStatus.OK);
    }

    @GetMapping("/companyjob/{companyId}")
    public ResponseEntity<ApiResponse<JobListResponse>> getJobListByCompanyId(
            @PathVariable(value = "companyId", required = false) String companyId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<JobListResponse> jobListByCompanyId = jobService.getAllJobByCompanyId(
                companyId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(jobListByCompanyId, HttpStatus.OK);
    }
}
