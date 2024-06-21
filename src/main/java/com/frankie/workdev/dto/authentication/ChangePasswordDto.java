package com.frankie.workdev.dto.authentication;

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
public class ChangePasswordDto {
    @NotEmpty(message = "Current password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String currentPassword;

    @NotEmpty(message = "New password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    @NotEmpty(message = "Confirm password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirmPassword;
}
