package com.frankie.workdev.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Change password response")
public class ChangePasswordResponse {

    @Schema(description = "New password")
    private String newPassword;
}
