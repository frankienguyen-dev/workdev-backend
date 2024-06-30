package com.frankie.workdev.dto.user.response;

import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.role.response.RoleResponse;
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
public class UserInfoResponse extends BaseUser<UploadFileResponse, RoleResponse, CompanyInfoResponse> {

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
    public UploadFileResponse getAvatar() {
        return super.getAvatar();
    }

    @Override
    public List<RoleResponse> getRoles() {
        return super.getRoles();
    }

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }
}
