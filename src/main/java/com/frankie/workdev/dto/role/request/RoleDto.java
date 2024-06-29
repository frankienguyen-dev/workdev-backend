package com.frankie.workdev.dto.role.request;

import com.frankie.workdev.dto.permission.PermissionInfo;
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
@Schema(description = "Role DTO")
public class RoleDto extends BaseRole<PermissionInfo> {
    @Schema(description = "Role created by", hidden = true)
    private JwtUserInfo createdBy;

    @Schema(description = "Role created at", hidden = true)
    private LocalDateTime createdAt;

    @Schema(description = "Role updated by", hidden = true)
    private JwtUserInfo updatedBy;

    @Schema(description = "Role updated at", hidden = true)
    private LocalDateTime updatedAt;

    @Schema(description = "Role deleted by", hidden = true)
    private JwtUserInfo deletedBy;

    @Schema(description = "Role deleted at", hidden = true)
    private LocalDateTime deletedAt;

    @Schema(description = "Role is deleted", hidden = true)
    private Boolean isDeleted;

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

//    @Override
//    public String getName() {
//        return super.getName();
//    }

    @Schema(hidden = true)
    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }

    @Schema(hidden = true)
    @Override
    public List<PermissionInfo> getPermissions() {
        return super.getPermissions();
    }
}
