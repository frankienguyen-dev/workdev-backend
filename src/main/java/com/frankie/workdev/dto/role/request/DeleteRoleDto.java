package com.frankie.workdev.dto.role.request;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Delete role DTO request")
public class DeleteRoleDto {

    @Schema(description = "Role Id")
    private String id;

    @Schema(description = "Role Deleted By")
    private JwtUserInfo deletedBy;

    @Schema(description = "Role Deleted At")
    private LocalDateTime deletedAt;

    @Schema(description = "Role Is Deleted")
    private Boolean isDeleted;

}
