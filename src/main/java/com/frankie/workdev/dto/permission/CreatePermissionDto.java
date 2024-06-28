package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Create permission DTO request")
public class CreatePermissionDto {
    @Schema(description = "Permission Id")
    private String id;

    @Schema(description = "Permission name")
    @NotEmpty(message = "name must not be empty")
    private String name;

    @Schema(description = "Permission path")
    @NotEmpty(message = "path must not be empty")
    private String path;

    @Schema(description = "Permission method")
    @NotEmpty(message = "method must not be empty")
    private String method;

    @Schema(description = "Permission module")
    @NotEmpty(message = "module must not be empty")
    private String module;

    @Schema(description = "Permission created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Permission created at")
    private LocalDateTime createdAt;
}
