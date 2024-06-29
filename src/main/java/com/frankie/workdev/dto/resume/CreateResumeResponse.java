package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobInfo;
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
@Schema(description = "Create Resume Response")
public class CreateResumeResponse extends BaseResume<UploadFileResponse, CompanyInfoResponse>{
    @Schema(description = "Resume job")
    private JobInfo job;

    @Schema(description = "Resume created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Resume created at")
    private LocalDateTime createdAt;

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }

    @Override
    public UploadFileResponse getResume() {
        return super.getResume();
    }
}
