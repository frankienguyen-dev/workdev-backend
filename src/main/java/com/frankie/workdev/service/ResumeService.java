package com.frankie.workdev.service;


import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.resume.*;

public interface ResumeService {
    ApiResponse<CreateResumeResponse> createResume(CreateResumeDto createResumeDto);

    ApiResponse<ResumeListResponse> getAllResumes(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<ResumeResponse> getResumeById(String id);

    ApiResponse<UpdateResumeResponse> updateResumeById(String id, UpdateResumeDto updateResumeDto);

    ApiResponse<DeleteResumeDto> deleteResumeById(String id);

    ApiResponse<ResumeListResponse> getResumeByUser(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<ResumeListResponse> searchResumeByEmail(String email,
                                                        int pageNo, int pageSize,
                                                        String sortBy, String sortDir);

    ApiResponse<ResumeListResponse> getAllResumesByJobId(String jobId, int pageNo, int pageSize,
                                                         String sortBy, String sortDir);


}
