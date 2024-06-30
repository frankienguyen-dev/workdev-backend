package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfoResponse;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.resume.ResumeInfo;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Job DTO")
public class JobResponse extends BaseJob<CompanyInfoResponse, CategoryInfoResponse> {
    @Schema(description = "Job resumes")
    private List<ResumeInfo> resumes;

    @Schema(description = "Job created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Job created at")
    private LocalDateTime createdAt;

    @Schema(description = "Job updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Job updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Job deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Job deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Job is deleted")
    private Boolean isDeleted;
}
