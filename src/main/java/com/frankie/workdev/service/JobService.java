package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.job.*;


public interface JobService {
    ApiResponse<CreateJobResponse> createJob(CreateJobDto createJobDto);

    ApiResponse<JobListResponse> getAllJobs(int pageNo, int pageSize,
                                            String sortBy, String sortDir);

    ApiResponse<JobResponse> getJobById(String id);

    ApiResponse<UpdateJobResponse> updateJobById(String id, UpdateJobDto updateJobDto);

    ApiResponse<DeleteJobDto> deleteJobById(String id);

    ApiResponse<JobListResponse> searchJob(String name, String location, Long salary,
                                           int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<JobResponse> favoriteJob(String id);

    ApiResponse<JobListResponse> getFavoriteJobs(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<JobListResponse> getJobListByUser(int pageNo,
                                                  int pageSize, String sortBy,
                                                  String sortDir);

    ApiResponse<JobListResponse> getAllJobByCompanyId(String companyId, int pageNo,
                                                      int pageSize, String sortBy, String sortDir);
}
