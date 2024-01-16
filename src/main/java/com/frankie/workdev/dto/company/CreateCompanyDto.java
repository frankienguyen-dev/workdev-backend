package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
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
public class CreateCompanyDto {
    private String id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String companyType;
    private String description;
    private String responsibility;
    private String address;
    private String email;
    private String phoneNumber;
    private String website;
    private Long teamSize;
    private LocalDateTime foundedDate;
    private FileUploadDto logo;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
}
