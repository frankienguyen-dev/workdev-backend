package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class UserDto {
    private String id;
    private String fullName;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    private FileUploadDto logo;
    private String address;
    private String phoneNumber;
    private String gender;
    private String title;
    private int age;
    private CompanyInfo company;
    private JwtUserInfo createdBy;
    private List<RoleDto> roles;
}
