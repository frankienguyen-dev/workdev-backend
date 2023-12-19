package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.entity.FileEntity;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.FileRepository;
import com.frankie.workdev.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private FileRepository fileRepository;

    @Override
    public ApiResponse<UploadFileResponse> uploadFile(MultipartFile file) {
        try {
            String uploadPath = "/Users/nguyenduongchanhtay/Desktop/WorkDev/src/main/java/com/frankie/workdev/upload";
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                Files.createDirectories(Paths.get(uploadPath));
            }
            if(file.isEmpty()) {
                return ApiResponse.error(
                        "Upload file failed",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }
            String defaultName = file.getOriginalFilename();
            if (defaultName != null) {
                String fileExtension = defaultName.substring(defaultName
                        .lastIndexOf(".") + 1);
                String uniqueFileName = generateUniqueFileName(fileExtension);
                String filePath = uploadPath + "/" + uniqueFileName;
                Files.copy(file.getInputStream(), Path.of(filePath),
                        StandardCopyOption.REPLACE_EXISTING);

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(uniqueFileName);
                fileEntity.setFileType(file.getContentType());
                fileEntity.setSize(file.getSize());
                fileEntity.setUploadTime(LocalDateTime.now());
                fileEntity.setData(file.getBytes());
                FileEntity saveFile = fileRepository.save(fileEntity);

                UploadFileResponse uploadFileResponse = new UploadFileResponse();
                uploadFileResponse.setId(saveFile.getId());
                uploadFileResponse.setFileName(saveFile.getFileName());
                uploadFileResponse.setFileType(saveFile.getFileType());
                uploadFileResponse.setSize(saveFile.getSize());
                uploadFileResponse.setUploadTime(saveFile.getUploadTime());
                return ApiResponse.success(
                        "Upload file successfully",
                        HttpStatus.OK,
                        uploadFileResponse
                );
            } else {
                return ApiResponse.error(
                        "Upload file failed",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }
        } catch (IOException e) {
            return ApiResponse.error(
                    "Upload file failed",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    @Override
    public FileEntity downloadFile(String id) {
        return fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File", "id", id)
        );
    }

    private String generateUniqueFileName(String fileExtension) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String randomChar = UUID.randomUUID().toString().substring(0, 8);
        return "file" + timestamp + "_" + randomChar + "." + fileExtension;
    }
}
