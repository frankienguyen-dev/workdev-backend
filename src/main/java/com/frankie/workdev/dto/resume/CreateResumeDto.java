package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobId;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.upload.BaseFileUpload;
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
public class CreateResumeDto extends BaseResume<FileUploadDto, CompanyInfo>{

    @NotNull(message = "Job should not be empty")
    @Valid
    @Schema(description = "Resume job")
    private JobId job;

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @Schema(hidden = true)
    @Override
    public JwtUserInfo getUser() {
        return super.getUser();
    }

    @NotNull(message = "Company should not be empty")
    @Valid
    @Override
    public CompanyInfo getCompany() {
        return super.getCompany();
    }

    @Schema(hidden = true)
    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @NotNull(message = "Resume should not be empty")
    @Valid
    @Override
    public FileUploadDto getResume() {
        return super.getResume();
    }
}
