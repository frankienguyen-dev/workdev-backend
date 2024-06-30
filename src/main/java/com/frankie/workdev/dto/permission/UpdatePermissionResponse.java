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
@Schema(description = "Update permission response")
public class UpdatePermissionResponse extends BasePermission {
    @Schema(description = "Permission updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Permission updated at")
    private LocalDateTime updatedAt;
}
