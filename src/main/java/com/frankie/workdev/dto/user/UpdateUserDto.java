package com.frankie.workdev.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String id;
    private String fullName;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private int age;
    private LocalDateTime updatedAt;
}
