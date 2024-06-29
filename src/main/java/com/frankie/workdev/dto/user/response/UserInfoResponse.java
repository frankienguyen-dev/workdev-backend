package com.frankie.workdev.dto.user.response;

import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobInfo;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.BaseFileUpload;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.dto.user.BaseUser;
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
public class UserInfoResponse extends BaseUser<UploadFileResponse, RoleResponse> {
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

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getEducation() {
        return super.getEducation();
    }

    @Override
    public String getExperience() {
        return super.getExperience();
    }

    @Override
    public UploadFileResponse getAvatar() {
        return super.getAvatar();
    }

    @Override
    public String getBiography() {
        return super.getBiography();
    }

    @Override
    public String getCoverLetter() {
        return super.getCoverLetter();
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public List<RoleResponse> getRoles() {
        return super.getRoles();
    }
}
