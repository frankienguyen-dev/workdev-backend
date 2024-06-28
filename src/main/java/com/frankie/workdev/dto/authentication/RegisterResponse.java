package com.frankie.workdev.dto.authentication;

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
@Schema(description = "Register response")
public class RegisterResponse {

    @Schema(description = "User id")
    private String id;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "User full name")
    private String fullName;

    @Schema(description = "User role")
    private String role;

    @Schema(description = "User created at")
    private LocalDateTime createdAt;
}
