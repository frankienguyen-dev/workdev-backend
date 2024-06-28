package com.frankie.workdev.dto.role.request;

import com.frankie.workdev.dto.permission.PermissionInfo;
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
public class RoleDto {

    @Schema(description = "Role Id", hidden = true)
    private String id;

    @Schema(description = "Role name")
    private String name;

    @Schema(description = "Role status", hidden = true)
    private Boolean isActive;

    @Schema(description = "Permissions", hidden = true)
    private List<PermissionInfo> permissions;

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

//    @Schema(description = "List of users in this role", hidden = true)
//    private List<JwtUserInfo> users;

}
