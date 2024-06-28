package com.frankie.workdev.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Change password DTO request")
public class ChangePasswordDto {
    @Schema(description = "Current password")
    @NotEmpty(message = "Current password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String currentPassword;

    @Schema(description = "New password")
    @NotEmpty(message = "New password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    @Schema(description = "Confirm password")
    @NotEmpty(message = "Confirm password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirmPassword;
}
