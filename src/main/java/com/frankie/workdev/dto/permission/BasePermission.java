package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePermission {
    @Schema(description = "Permission Id")
    private String id;

    @NotEmpty(message = "Permission name must not be empty")
    @Schema(description = "Permission name")
    private String name;

    @NotEmpty(message = "Permission path must not be empty")
    @Schema(description = "Permission path")
    private String path;

    @NotEmpty(message = "Permission method must not be empty")
    @Schema(description = "Permission method")
    private String method;

    @NotEmpty(message = "Permission module must not be empty")
    @Schema(description = "Permission module")
    private String module;

}
