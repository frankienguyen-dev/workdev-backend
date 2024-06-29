package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobId;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Update resume DTO request")
public class UpdateResumeDto extends BaseResume<FileUploadDto, CompanyInfo> {

    @Schema(hidden = true)
    @Override
    public FileUploadDto getResume() {
        return super.getResume();
    }

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @Schema(hidden = true)
    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Schema(hidden = true)
    @Override
    public JwtUserInfo getUser() {
        return super.getUser();
    }

    @Schema(hidden = true)
    @Override
    public CompanyInfo getCompany() {
        return super.getCompany();
    }

    @NotNull(message = "Resume status should not be empty")
    @Override
    public String getStatus() {
        return super.getStatus();
    }
}
