package com.frankie.workdev.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {
    private String id;
    private String fileName;
    private String fileType;
    private long size;
    private LocalDateTime uploadTime;
}
