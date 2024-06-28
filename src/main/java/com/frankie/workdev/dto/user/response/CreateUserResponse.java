package com.frankie.workdev.dto.user.response;


import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Create User DTO Response")
public class CreateUserResponse {
    @Schema(description = "User Id")
    private String id;

    @Schema(description = "User full name")
    @NotEmpty(message = "Full name should not be empty")
    private String fullName;

    @Schema(description = "User Email")
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Schema(description = "User address")
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Schema(description = "User phone number")
    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @Schema(description = "User gender")
    @NotEmpty(message = "Gender should not be empty")
    private String gender;

    @Schema(description = "User title")
    @NotEmpty(message = "Title should not be empty")
    private String title;

    @Schema(description = "User education")
    @NotEmpty(message = "Education should not be empty")
    private String education;

    @Schema(description = "User experience")
    @NotEmpty(message = "Experience should not be empty")
    private String experience;

    @Schema(description = "User avatar")
    private UploadFileResponse avatar;

    @Schema(description = "User biography")
    @NotEmpty(message = "Biography should not be empty")
    private String biography;

    @Schema(description = "User cover letter")
    @NotEmpty(message = "Cover letter should not be empty")
    private String coverLetter;

    @Schema(description = "User age")
    @NotNull(message = "Age should not be empty")
    private int age;

    @Schema(description = "User job list")
    private List<JobDto> jobs;

    @Schema(description = "User roles")
    @NotEmpty(message = "Roles should not be empty")
    private List<RoleResponse> roles;

    @Schema(description = "User company")
    private CompanyInfoResponse company;

    @Schema(description = "User created by")
    private JwtUserInfo createdBy;

    @Schema(description = "User created at")
    private LocalDateTime createdAt;
}
