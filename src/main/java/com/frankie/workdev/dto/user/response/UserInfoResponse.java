package com.frankie.workdev.dto.user.response;

import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User Info DTO")
public class UserInfoResponse {

    @Schema(description = "User Id")
    private String id;

    @Schema(description = "User full name")
    private String fullName;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "User address")
    private String address;

    @Schema(description = "User avatar")
    private UploadFileResponse avatar;

    @Schema(description = "User education")
    private String education;

    @Schema(description = "User experience")
    private String experience;

    @Schema(description = "User phone number")
    private String phoneNumber;

    @Schema(description = "User gender")
    private String gender;

    @Schema(description = "User title")
    private String title;

    @Schema(description = "User age")
    private int age;

    @Schema(description = "User biography")
    private String biography;

    @Schema(description = "User cover letter")
    private String coverLetter;

    @Schema(description = "User job list")
    private List<JobInfo> jobs;

    @Schema(description = "User roles")
    private List<RoleResponse> roles;

    @Schema(description = "User company")
    private CompanyInfoResponse company;

    @Schema(description = "User created by")
    private JwtUserInfo createdBy;

    @Schema(description = "User created at")
    private LocalDateTime createdAt;

    @Schema(description = "User updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "User updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "User deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "User deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "User is deleted")
    private Boolean isDeleted;
}
