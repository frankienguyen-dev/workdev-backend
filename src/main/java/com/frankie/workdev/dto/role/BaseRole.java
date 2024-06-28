package com.frankie.workdev.dto.role;

import com.frankie.workdev.dto.permission.BasePermission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRole<T extends BasePermission> {
    @Schema(description = "Role Id")
    private String id;

    @NotEmpty(message = "Role name should not be empty")
    @Schema(description = "Role name")
    private String name;

    @NotNull(message = "Role status should not be null")
    @Schema(description = "Role status")
    private Boolean isActive;


    @Schema(description = "List of permissions")
    @NotEmpty(message = "Permissions should not be empty")
    private List<T> permissions;
}
