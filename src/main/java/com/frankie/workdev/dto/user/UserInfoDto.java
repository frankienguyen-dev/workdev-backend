package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.resume.ResumeInfoDto;
import com.frankie.workdev.dto.role.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.entity.Job;
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
public class UserInfoDto {
    private String id;
    private String fullName;
    private String email;
    private String address;
    private FileUploadDto avatar;
    private String education;
    private String experience;
    private String phoneNumber;
    private String gender;
    private String title;
    private int age;
    private List<JobInfo> jobs;
    private List<RoleDto> roles;
    private CompanyInfo company;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private boolean isDeleted;
}
