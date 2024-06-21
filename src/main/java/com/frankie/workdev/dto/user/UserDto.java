package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "Education should not be empty")
    private String education;

    @NotEmpty(message = "Experience should not be empty")
    private String experience;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    private FileUploadDto avatar;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "Gender should not be empty")
    private String gender;

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Biography should not be empty")
    private String biography;

    @NotEmpty(message = "Cover letter should not be empty")
    private String coverLetter;

    @NotNull(message = "Age should not be empty")
    private int age;
    private CompanyInfo company;
    private JwtUserInfo createdBy;

    @NotEmpty(message = "Roles should not be empty")
    private List<RoleDto> roles;
}
