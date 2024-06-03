package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.entity.FileEntity;
import com.frankie.workdev.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/files")
public class UploadFileController {

    private UploadFileService uploadFileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<UploadFileResponse>> upload(
            @RequestParam("file") MultipartFile file
    ) {
        ApiResponse<UploadFileResponse> upload = uploadFileService.uploadFile(file);
        return new ResponseEntity<>(upload, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") String id) {
        FileEntity fileData = uploadFileService.downloadFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileData.getFileName());
        headers.setContentLength(fileData.getSize());
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(MediaType.parseMediaType(fileData.getFileType()));
        return new ResponseEntity<>(fileData.getData(), headers, HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String fileName) {
        FileEntity imageData = uploadFileService.getFileByFileName(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(imageData.getSize());
        return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
    }
}
