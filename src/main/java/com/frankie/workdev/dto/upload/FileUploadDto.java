package com.frankie.workdev.dto.upload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "File Upload DTO")
public class FileUploadDto {

    @Schema(description = "File id")
    @NotEmpty(message = "File id should not be empty")
    private String id;

    @Schema(description = "File name", hidden = true)
    private String fileName;

    @Schema(description = "File type", hidden = true)
    private String fileType;

    @Schema(description = "File size", hidden = true)
    private long size;

    @Schema(description = "File upload time", hidden = true)
    private LocalDateTime uploadTime;
}
