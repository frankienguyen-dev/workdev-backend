package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create Resume DTO request")
public class CreateResumeDto {
    @Schema(description = "Resume Id")
    private String id;

    @Schema(description = "Resume File")
    @NotNull(message = "Resume should not be empty")
    private FileUploadDto resume;

    @Schema(description = "Resume Url")
    private String url;

    @Schema(description = "Company Info")
    @NotNull(message = "Company should not be empty")
    private CompanyInfo company;

    @Schema(description = "Job Info")
    @Valid
    @NotNull(message = "Job should not be empty")
    private JobInfo job;

    @Schema(description = "User Info")
    private JwtUserInfo user;

    @Schema(description = "Resume status")
    private String status = "PENDING";

    @Schema(description = "Resume created at")
    private LocalDateTime createdAt;

    @Schema(description = "Resume created by")
    private JwtUserInfo createdBy;
}
