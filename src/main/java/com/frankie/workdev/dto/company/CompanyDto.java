package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Company DTO")
public class CompanyDto {
    @Schema(description = "Company Id")
    private String id;

    @Schema(description = "Company Name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(description = "Company Description")
    private String description;

    @Schema(description = "Company Type")
    private String companyType;

    @Schema(description = "Company Benefit")
    private String companyBenefit;

    @Schema(description = "Company Address")
    private String address;

    @Schema(description = "Company Email")
    private String email;

    @Schema(description = "Company Phone Number")
    private String phoneNumber;

    @Schema(description = "Company Website")
    private String website;

    @Schema(description = "Company Team Size")
    private Long teamSize;

    @Schema(description = "Company founded date")
    private LocalDateTime foundedDate;

    @Schema(description = "Company Logo")
    private FileUploadDto logo;

    @Schema(description = "Company Banner")
    private FileUploadDto banner;

    @Schema(description = "Company Users")
    private List<JwtUserInfo> users;

    @Schema(description = "Company Created By")
    private JwtUserInfo createdBy;

    @Schema(description = "Company Created At")
    private LocalDateTime createdAt;

    @Schema(description = "Company Updated By")
    private JwtUserInfo updatedBy;

    @Schema(description = "Company Updated At")
    private LocalDateTime updatedAt;

    @Schema(description = "Company Deleted By")
    private JwtUserInfo deletedBy;

    @Schema(description = "Company Deleted At")
    private LocalDateTime deletedAt;

    @Schema(description = "Company Is Deleted")
    private Boolean isDeleted;
}
