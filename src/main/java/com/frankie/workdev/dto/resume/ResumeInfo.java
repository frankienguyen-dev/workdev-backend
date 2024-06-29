package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
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
@Schema(description = "Resume Info")
public class ResumeInfo extends BaseResume<UploadFileResponse, CompanyInfoResponse> {
    @Schema(description = "Resume created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Resume created at")
    private LocalDateTime createdAt;

    @Schema(description = "Resume updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Resume updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Resume deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Resume deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Resume is deleted")
    private Boolean isDeleted;

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public UploadFileResponse getResume() {
        return super.getResume();
    }

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }
}
