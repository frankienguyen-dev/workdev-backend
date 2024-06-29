package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.BaseFileUpload;
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
@Schema(description = "Base company")
public abstract class BaseCompany<T extends BaseFileUpload> {
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
    private T logo;

    @Schema(description = "Company Banner")
    private T banner;

    @Schema(description = "Company Users")
    private List<JwtUserInfo> users;
}
