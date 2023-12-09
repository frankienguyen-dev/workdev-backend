package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.CompanyInfo;
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
public class CreateUserDto {
    private String id;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private int age;
    private CompanyInfo company;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
}
