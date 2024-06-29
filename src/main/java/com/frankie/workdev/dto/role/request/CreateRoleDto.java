package com.frankie.workdev.dto.role.request;

import com.frankie.workdev.dto.permission.PermissionInfo;
import com.frankie.workdev.dto.role.BaseRole;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Create role DTO request")
public class CreateRoleDto extends BaseRole<PermissionInfo> {

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

//    @Override
//    public String getName() {
//        return super.getName();
//    }
//
//    @Override
//    public Boolean getIsActive() {
//        return super.getIsActive();
//    }
//
//    @Override
//    public List<PermissionInfo> getPermissions() {
//        return super.getPermissions();
//    }
}
