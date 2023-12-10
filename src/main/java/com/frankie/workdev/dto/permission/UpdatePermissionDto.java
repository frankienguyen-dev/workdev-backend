package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermissionDto {
    private String id;
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotEmpty(message = "path must not be empty")
    private String module;

    @NotEmpty(message = "method must not be empty")
    private String method;

    @NotEmpty(message = "path must not be empty")
    private String path;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}
