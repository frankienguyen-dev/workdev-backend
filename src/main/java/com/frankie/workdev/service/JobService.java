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

    ApiResponse<JobDto> favoriteJob(String id);

    ApiResponse<JobResponse> getFavoriteJobs(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<JobResponse> getJobListByUser(int pageNo,
                                              int pageSize, String sortBy,
                                              String sortDir);

    ApiResponse<JobResponse> getAllJobByCompanyId(String companyId, int pageNo,
                                                  int pageSize, String sortBy, String sortDir);
}
