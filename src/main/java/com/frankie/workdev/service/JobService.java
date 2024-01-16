package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.job.*;


public interface JobService {
    ApiResponse<CreateJobDto> createJob(CreateJobDto createJobDto);

    ApiResponse<JobResponse> getAllJobs(int pageNo, int pageSize,
                                        String sortBy, String sortDir);

    ApiResponse<JobDto> getJobById(String id);

    ApiResponse<UpdateJobDto> updateJobById(String id, UpdateJobDto updateJobDto);

    ApiResponse<DeleteJobDto> deleteJobById(String id);

    ApiResponse<JobResponse> searchJob(String name, String location, Long salary,
                                       int pageNo, int pageSize, String sortBy, String sortDir);
}
