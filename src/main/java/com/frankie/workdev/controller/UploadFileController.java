package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/files")
public class UploadFileController {

    private UploadFileService uploadFileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<UploadFileResponse>> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        ApiResponse<UploadFileResponse> upload = uploadFileService.uploadFile(file);
        return new ResponseEntity<>(upload, HttpStatus.OK);
    }

}
