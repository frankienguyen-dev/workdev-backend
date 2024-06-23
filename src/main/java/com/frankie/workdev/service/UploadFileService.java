package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.upload.DownloadFileResponse;
import com.frankie.workdev.dto.upload.FileUploadResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.entity.FileEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    ApiResponse<UploadFileResponse> uploadFile(MultipartFile file);

    FileEntity downloadFile(String id);

    FileEntity getFileByFileName(String fileName);

    ApiResponse<FileUploadResponse> getOrphanFiles(int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteOrphanFiles() throws IOException;
}
