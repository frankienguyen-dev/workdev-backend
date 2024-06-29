package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Permission Info")
public class PermissionInfo extends BasePermission {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

//    @Override
//    public String getName() {
//        return super.getName();
//    }

    @Schema(hidden = true)
    @Override
    public String getPath() {
        return super.getPath();
    }

    @Schema(hidden = true)
    @Override
    public String getMethod() {
        return super.getMethod();
    }

    @Schema(hidden = true)
    @Override
    public String getModule() {
        return super.getModule();
    }
}
