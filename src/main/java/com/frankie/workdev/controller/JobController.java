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
    public ResponseEntity<ApiResponse<CreateJobDto>> createJob(
            @RequestBody @Valid CreateJobDto createJobDto) {
        ApiResponse<CreateJobDto> createJob = jobService.createJob(createJobDto);
        return new ResponseEntity<>(createJob, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<JobResponse>> getAllJobs(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<JobResponse> getAllJobs = jobService.getAllJobs(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllJobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDto>> getJobById(@PathVariable("id") String id) {
        ApiResponse<JobDto> getJobById = jobService.getJobById(id);
        return new ResponseEntity<>(getJobById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateJobDto>> updateJobById(
            @PathVariable("id") String id, @RequestBody UpdateJobDto updateJobDto) {
        ApiResponse<UpdateJobDto> updateJob = jobService.updateJobById(id, updateJobDto);
        return new ResponseEntity<>(updateJob, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteJobDto>> deletedJobById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteJobDto> deletedJob = jobService.deleteJobById(id);
        return new ResponseEntity<>(deletedJob, HttpStatus.OK);
    }
}
