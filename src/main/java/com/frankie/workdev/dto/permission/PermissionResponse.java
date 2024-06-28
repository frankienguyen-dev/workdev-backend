package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Permission Response")
public class PermissionResponse extends BasePermission {
    @Schema(description = "Permission created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Permission created at")
    private LocalDateTime createdAt;

    @Schema(description = "Permission updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Permission updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Permission deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Permission deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Permission is deleted")
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
    public String getPath() {
        return super.getPath();
    }

    @Override
    public String getMethod() {
        return super.getMethod();
    }

    @Override
    public String getModule() {
        return super.getModule();
    }

}
