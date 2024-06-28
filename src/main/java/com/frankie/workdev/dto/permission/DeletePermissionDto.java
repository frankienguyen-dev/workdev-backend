package com.frankie.workdev.dto.permission;

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
@Schema(description = "Delete permission dto request")
public class DeletePermissionDto {

    @Schema(description = "Permission id")
    private String id;

    @Schema(description = "Permission deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Permission deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Permission is deleted")
    private Boolean isDeleted;
}
