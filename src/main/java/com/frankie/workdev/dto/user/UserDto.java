package com.frankie.workdev.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String fullName;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;
    private String address;
    private String phoneNumber;
    private String gender;
    private int age;
}
