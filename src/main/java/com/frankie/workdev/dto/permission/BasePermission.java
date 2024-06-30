package com.frankie.workdev.dto.permission;

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

    @Schema(description = "Permission name")
    private String name;

    @Schema(description = "Permission path")
    private String path;

    @Schema(description = "Permission method")
    private String method;

    @Schema(description = "Permission module")
    private String module;

}
