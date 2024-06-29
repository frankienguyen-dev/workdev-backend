package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.upload.OrphanFileListResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.entity.FileEntity;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.FileRepository;
import com.frankie.workdev.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final ModelMapper modelMapper;
    private FileRepository fileRepository;

    @Override
    public ApiResponse<UploadFileResponse> uploadFile(MultipartFile file) {
        try {

            String uploadPath = "/Users/nguyenduongchanhtay/Desktop/workdev-backend/src/main/java/com/frankie/workdev/upload";
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                Files.createDirectories(Paths.get(uploadPath));
            }
            if (file.isEmpty()) {
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

    @Override
    public FileEntity getFileByFileName(String fileName) {
        try {
            FileEntity fileEntity = fileRepository.findByFileName(fileName);
            if (fileEntity == null) {
                throw new ResourceNotFoundException("File", "fileName", fileName);
            }
            return fileEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<OrphanFileListResponse> getOrphanFiles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<FileEntity> files = fileRepository.getOrphanFiles(pageable);
        List<FileEntity> fileEntities = files.getContent();
        List<UploadFileResponse> fileUploadDtoList = fileEntities.stream().map(
                file -> {
                    try {
                        return modelMapper.map(file, UploadFileResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(files.isLast());
        metaData.setPageNo(files.getNumber());
        metaData.setTotalElements(files.getTotalElements());
        metaData.setPageSize(files.getSize());
        metaData.setTotalPages(files.getTotalPages());
        OrphanFileListResponse fileUploadResponse = new OrphanFileListResponse();
        fileUploadResponse.setMeta(metaData);
        fileUploadResponse.setData(fileUploadDtoList);
        return ApiResponse.success(
                "Get all files successfully",
                HttpStatus.OK,
                fileUploadResponse
        );
    }


    @Override
    @Scheduled(cron = "0 0 7 ? * SAT")
//    @Scheduled(fixedRate = 30000)
    public void deleteOrphanFiles() throws IOException {
        try {
            List<FileEntity> fileEntities = fileRepository.findOrphanFiles();
            for (FileEntity file : fileEntities) {
                String filePath = "/Users/nguyenduongchanhtay/Desktop/workdev-backend/src/main/java/com/frankie/workdev/upload/" + file.getFileName();
                Files.deleteIfExists(Paths.get(filePath));
                fileRepository.delete(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String randomChar = UUID.randomUUID().toString().substring(0, 8);
        return "file" + timestamp + "_" + randomChar + "." + fileExtension;
    }
}
