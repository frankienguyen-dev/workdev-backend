package com.frankie.workdev.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Create permission DTO request")
public class CreatePermissionDto extends BasePermission {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @NotEmpty(message = "Permission name must not be empty")
    @Override
    public String getName() {
        return super.getName();
    }

    @NotEmpty(message = "Permission path must not be empty")
    @Override
    public String getPath() {
        return super.getPath();
    }

    @NotEmpty(message = "Permission method must not be empty")
    @Override
    public String getMethod() {
        return super.getMethod();
    }

    @NotEmpty(message = "Permission module must not be empty")
    @Override
    public String getModule() {
        return super.getModule();
    }
}
