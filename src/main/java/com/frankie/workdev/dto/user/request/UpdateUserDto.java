package com.frankie.workdev.dto.user.request;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
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
@Schema(description = "Update User Dto Request")
public class UpdateUserDto {
    @Schema(description = "User Id", hidden = true)
    private String id;

    @Schema(description = "User full name")
    @NotEmpty(message = "Full name should not be empty")
    private String fullName;

    @Schema(description = "User email")
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Schema(description = "User address")
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Schema(description = "User phone number")
    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @Schema(description = "User avatar")
    private FileUploadDto avatar;

    @Schema(description = "User gender")
    @NotEmpty(message = "Gender should not be empty")
    private String gender;

    @Schema(description = "User education")
    @NotEmpty(message = "Education should not be empty")
    private String education;

    @Schema(description = "User experience")
    @NotEmpty(message = "Experience should not be empty")
    private String experience;

    @Schema(description = "User title")
    @NotEmpty(message = "Title should not be empty")
    private String title;

    @Schema(description = "User biography")
    @NotEmpty(message = "Biography should not be empty")
    private String biography;

    @Schema(description = "User cover letter")
    @NotEmpty(message = "Cover letter should not be empty")
    private String coverLetter;

    @Schema(description = "User age")
    @NotNull(message = "Age should not be empty")
    private int age;

    @Schema(description = "User job list", hidden = true)
    private List<JobDto> jobs;

    @Schema(description = "User roles")
    @NotEmpty(message = "Roles should not be empty")
    private List<RoleDto> roles;

    @Schema(description = "User company")
    private CompanyInfo company;

    @Schema(description = "User updated by", hidden = true)
    private JwtUserInfo updatedBy;

    @Schema(description = "User updated at", hidden = true)
    private LocalDateTime updatedAt;
}
