package com.frankie.workdev.dto.upload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Base file upload")
public abstract class BaseFileUpload {
    @Schema(description = "File id")
    private String id;

    @Schema(description = "File name")
    private String fileName;

    @Schema(description = "File type")
    private String fileType;

    @Schema(description = "File size")
    private long size;

    @Schema(description = "Upload time")
    private LocalDateTime uploadTime;
}
