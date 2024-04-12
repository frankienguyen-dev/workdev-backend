package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeInfoDto {
    private String id;
    private String url;
    private FileUploadDto resume;
    private CompanyInfo company;
    private JobDto job;
    private String email;
    private String status;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private boolean isDeleted;
}
