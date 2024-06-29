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
@Schema(description = "File Upload DTO")
public class FileUploadDto extends BaseFileUpload {
    @NotEmpty(message = "File name should not be empty")
    @Override
    public String getId() {
        return super.getId();
    }

    @Schema(hidden = true)
    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Schema(hidden = true)
    @Override
    public String getFileType() {
        return super.getFileType();
    }

    @Schema(hidden = true)
    @Override
    public long getSize() {
        return super.getSize();
    }

    @Schema(hidden = true)
    @Override
    public LocalDateTime getUploadTime() {
        return super.getUploadTime();
    }
}
