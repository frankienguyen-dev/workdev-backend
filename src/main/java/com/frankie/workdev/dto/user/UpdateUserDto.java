package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class UpdateUserDto {
    private String id;
    private String fullName;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;
    private String address;
    private String phoneNumber;
    private FileUploadDto avatar;
    private String gender;
    private String title;
    private int age;
    private List<JobDto> jobs;
    private List<RoleDto> roles;
    private CompanyInfo company;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}
