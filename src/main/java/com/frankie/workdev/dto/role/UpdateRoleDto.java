package com.frankie.workdev.dto.role;

import com.frankie.workdev.dto.permission.PermissionInfo;
import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class UpdateRoleDto {
    private String id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Role status should not be empty")
    private Boolean isActive;

    @NotEmpty(message = "Permissions should not be empty")
    private List<PermissionInfo> permissions;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}
