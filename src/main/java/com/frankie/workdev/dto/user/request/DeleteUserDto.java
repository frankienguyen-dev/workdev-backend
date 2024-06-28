package com.frankie.workdev.dto.user.request;

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
@Schema(description = "Delete User DTO Request")
public class DeleteUserDto {

    @Schema(description = "User Id")
    private String id;

    @Schema(description = "User Deleted At")
    private LocalDateTime deletedAt;

    @Schema(description = "User Deleted By")
    private JwtUserInfo deletedBy;

    @Schema(description = "User Is Deleted")
    private Boolean isDeleted;
}
