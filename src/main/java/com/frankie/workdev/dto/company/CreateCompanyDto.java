package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotEmpty(message = "Company type should not be empty")
    private String companyType;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "Company benefit should not be empty")
    private String companyBenefit;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "Website should not be empty")
    private String website;

    @NotNull(message = "Team size should not be empty")
    private Long teamSize;

    @NotNull(message = "Founded date should not be empty")
    @PastOrPresent(message = "Founded date must be in the past or present")
    private LocalDateTime foundedDate;

    @NotNull(message = "Logo should not be empty")
    @Valid
    private FileUploadDto logo;

    @NotNull(message = "Banner should not be empty")
    @Valid
    private FileUploadDto banner;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
}
