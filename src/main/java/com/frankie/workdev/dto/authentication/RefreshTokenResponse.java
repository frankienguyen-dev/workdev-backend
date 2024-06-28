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
@Schema(description = "Refresh token response")
public class RefreshTokenResponse {
    @Schema(description = "Refresh token")
    private String refreshToken;
}
