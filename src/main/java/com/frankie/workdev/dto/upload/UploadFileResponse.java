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
public class UploadFileResponse {
    private String id;
    private String fileName;
    private String fileType;
    private long size;
    private byte[] data;
    private LocalDateTime uploadTime;
}
