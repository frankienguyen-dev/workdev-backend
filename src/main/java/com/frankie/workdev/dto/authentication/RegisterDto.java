package com.frankie.workdev.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Register user DTO request")
public class RegisterDto {
    @Schema(description = "User email")
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "User full name")
    @NotEmpty(message = "Full name is required")
    private String fullName;

    @Schema(description = "User password")
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "User role")
    @NotEmpty(message = "Role name is required")
    private String role;
}
