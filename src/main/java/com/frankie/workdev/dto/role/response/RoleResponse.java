package com.frankie.workdev.dto.role.response;

import com.frankie.workdev.dto.permission.PermissionResponse;
import com.frankie.workdev.dto.role.BaseRole;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
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
@Schema(description = "Role Response")
public class RoleResponse extends BaseRole<PermissionResponse> {
    @Schema(description = "Role created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Role created at")
    private LocalDateTime createdAt;

    @Schema(description = "Role updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Role updated at")
    private LocalDateTime updatedAt;


    @Schema(description = "Role deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Role deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Role is deleted")
    private Boolean isDeleted;

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        return super.getPermissions();
    }
}
