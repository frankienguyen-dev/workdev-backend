package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Create Company DTO Request")
public class CreateCompanyDto {

    @Schema(description = "Company Id")
    private String id;

    @Schema(description = "Company Name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(description = "Company Type")
    @NotEmpty(message = "Company type should not be empty")
    private String companyType;

    @Schema(description = "Company Description")
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @Schema(description = "Company Benefit")
    @NotEmpty(message = "Company benefit should not be empty")
    private String companyBenefit;

    @Schema(description = "Company Address")
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Schema(description = "Company Email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Company Phone Number")
    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @Schema(description = "Company Website")
    @NotEmpty(message = "Website should not be empty")
    private String website;

    @Schema(description = "Company Team Size")
    @NotNull(message = "Team size should not be empty")
    private Long teamSize;

    @Schema(description = "Company founded date")
    @NotNull(message = "Founded date should not be empty")
    @PastOrPresent(message = "Founded date must be in the past or present")
    private LocalDateTime foundedDate;

    @Schema(description = "Company Logo")
    @NotNull(message = "Logo should not be empty")
    @Valid
    private FileUploadDto logo;

    @Schema(description = "Company Banner")
    @NotNull(message = "Banner should not be empty")
    @Valid
    private FileUploadDto banner;

    @Schema(description = "Company Created By")
    private JwtUserInfo createdBy;

    @Schema(description = "Company Created At")
    private LocalDateTime createdAt;
}
