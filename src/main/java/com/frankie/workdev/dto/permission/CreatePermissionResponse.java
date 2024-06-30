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
@Schema(description = "Create permission response")
public class CreatePermissionResponse extends BasePermission {
    @Schema(description = "Permission created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Permission created at")
    private LocalDateTime createdAt;
}
