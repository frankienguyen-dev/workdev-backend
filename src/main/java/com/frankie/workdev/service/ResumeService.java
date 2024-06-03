package com.frankie.workdev.service;


import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.resume.*;

public interface ResumeService {
    ApiResponse<CreateResumeDto> createResume(CreateResumeDto createResumeDto);

    ApiResponse<ResumeResponse> getAllResumes(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<ResumeInfoDto> getResumeById(String id);

    ApiResponse<UpdateResumeDto> updateResumeById(String id, UpdateResumeDto updateResumeDto);

    ApiResponse<DeleteResumeDto> deleteResumeById(String id);

    ApiResponse<ResumeResponse> getResumeByUser(int pageNo, int pageSize, String sortBy, String sortDir);

    ApiResponse<ResumeResponse> searchResumeByEmail(String email,
                                                    int pageNo, int pageSize,
                                                    String sortBy, String sortDir);

    ApiResponse<ResumeResponse> getAllResumesByJobId(String jobId, int pageNo, int pageSize,
                                                    String sortBy, String sortDir);


}
