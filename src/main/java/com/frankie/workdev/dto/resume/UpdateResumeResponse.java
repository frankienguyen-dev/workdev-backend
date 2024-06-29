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
@Schema(description = "Update resume response")
public class UpdateResumeResponse extends BaseResume<UploadFileResponse, CompanyInfoResponse>{

    @Schema(description = "Resume job")
    private JobInfo job;

    @Schema(description = "Resume updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Resume updated at")
    private LocalDateTime updatedAt;

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }

    @Override
    public UploadFileResponse getResume() {
        return super.getResume();
    }
}
