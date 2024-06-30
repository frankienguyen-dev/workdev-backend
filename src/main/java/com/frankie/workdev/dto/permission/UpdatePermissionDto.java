package com.frankie.workdev.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Update permission DTO request")
public class UpdatePermissionDto extends BasePermission {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @NotEmpty(message = "name must not be empty")
    @Override
    public String getName() {
        return super.getName();
    }

    @NotEmpty(message = "path must not be empty")
    @Override
    public String getPath() {
        return super.getPath();
    }

    @NotEmpty(message = "method must not be empty")
    @Override
    public String getMethod() {
        return super.getMethod();
    }

    @NotEmpty(message = "path must not be empty")
    @Override
    public String getModule() {
        return super.getModule();
    }
}
