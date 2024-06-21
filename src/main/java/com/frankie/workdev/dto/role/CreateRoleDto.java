package com.frankie.workdev.dto.role;

import com.frankie.workdev.dto.permission.PermissionInfo;
import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateRoleDto {
    private String id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Role status should not be empty")
    private Boolean isActive;

    @NotEmpty(message = "Permissions should not be empty")
    private List<PermissionInfo> permissions;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
}
