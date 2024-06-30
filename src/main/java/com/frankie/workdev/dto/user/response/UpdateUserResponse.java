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
@Schema(description = "Update User Response")
public class UpdateUserResponse extends BaseUser<UploadFileResponse, RoleResponse, CompanyInfoResponse> {
    @Schema(description = "User updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "User updated at")
    private LocalDateTime updatedAt;

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
