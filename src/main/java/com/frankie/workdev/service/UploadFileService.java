package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.upload.OrphanFileListResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    ApiResponse<UploadFileResponse> uploadFile(MultipartFile file);

    FileEntity downloadFile(String id);

    FileEntity getFileByFileName(String fileName);

    ApiResponse<OrphanFileListResponse> getOrphanFiles(int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteOrphanFiles() throws IOException;
}
