package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.upload.FileUploadDto;
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
public class ResumeInfo {

    @Schema(description = "Resume Id")
    private String id;

    @Schema(description = "Resume Url")
    private String url;

    @Schema(description = "Resume File")
    private FileUploadDto resume;

    @Schema(description = "Company Info")
    private CompanyInfo company;

    @Schema(description = "User Info")
    private JwtUserInfo user;

    @Schema(description = "Resume status")
    private String status;

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
}
