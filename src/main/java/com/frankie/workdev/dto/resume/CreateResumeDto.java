package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class CreateResumeDto {
    private String id;

    @NotNull(message = "Resume should not be empty")
    private FileUploadDto resume;
    private String url;

    @NotNull(message = "Company should not be empty")
    private CompanyInfo company;

    @Valid
    @NotNull(message = "Job should not be empty")
    private JobInfo job;
    private JwtUserInfo user;
    private String status = "PENDING";
    private LocalDateTime createdAt;
    private JwtUserInfo createdBy;
}
