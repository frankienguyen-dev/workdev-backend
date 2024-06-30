package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.company.BaseCompany;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.permission.BasePermission;
import com.frankie.workdev.dto.role.BaseRole;
import com.frankie.workdev.dto.upload.BaseFileUpload;
import com.frankie.workdev.dto.upload.FileUploadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUser<T extends BaseFileUpload,
        K extends BaseRole<? extends BasePermission>, R> {
    @Schema(description = "User Id")
    private String id;

    @Schema(description = "User full name")
    private String fullName;

    @Schema(description = "User Email")
    private String email;

    @Schema(description = "User address")
    private String address;

    @Schema(description = "User phone number")
    private String phoneNumber;

    @Schema(description = "User gender")
    private String gender;

    @Schema(description = "User title")
    private String title;

    @Schema(description = "User education")
    private String education;

    @Schema(description = "User experience")
    private String experience;

    @Schema(description = "User avatar")
    private T avatar;

    @Schema(description = "User biography")
    private String biography;

    @Schema(description = "User cover letter")
    private String coverLetter;

    @Schema(description = "User age")
    private int age;

    @Schema(description = "User roles")
    private List<K> roles;

    @Schema(description = "User company")
    private R company;
}
