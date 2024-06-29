package com.frankie.workdev.dto.upload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Upload File Response")
public class UploadFileResponse extends BaseFileUpload {
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public String getFileType() {
        return super.getFileType();
    }

    @Override
    public long getSize() {
        return super.getSize();
    }

    @Override
    public LocalDateTime getUploadTime() {
        return super.getUploadTime();
    }
}
