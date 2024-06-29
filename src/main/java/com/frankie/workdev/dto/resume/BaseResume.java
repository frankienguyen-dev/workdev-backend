package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Base Resume")
public abstract class BaseResume<T extends BaseFileUpload, R> {
    @Schema(description = "Resume Id")
    private String id;

    @Schema(description = "Resume Url")
    private String url;

    @Schema(description = "Resume File")
    private T resume;

    @Schema(description = "Company Info")
    private R company;

    @Schema(description = "User Info")
    private JwtUserInfo user;

    @Schema(description = "Resume status")
    private String status;
}
