package com.frankie.workdev.dto.user.request;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User Id", hidden = true)
    private String id;

    @Schema(description = "User full name")
    private String fullName;

    @Schema(description = "User email")
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Schema(description = "User education")
    @NotEmpty(message = "Education should not be empty")
    private String education;

    @Schema(description = "User experience")
    @NotEmpty(message = "Experience should not be empty")
    private String experience;

    @Schema(description = "User password")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @Schema(description = "User avatar")
    private FileUploadDto avatar;

    @Schema(description = "User address")
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Schema(description = "User phone number")
    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @Schema(description = "User gender")
    @NotEmpty(message = "Gender should not be empty")
    private String gender;

    @Schema(description = "User title")
    @NotEmpty(message = "Title should not be empty")
    private String title;

    @Schema(description = "User biography")
    private String biography;

    @Schema(description = "User cover letter")
    private String coverLetter;

    @Schema(description = "User age")
    @NotNull(message = "Age should not be empty")
    private int age;

    @Schema(description = "User company")
    private CompanyInfo company;

    @Schema(description = "User created by", hidden = true)
    private JwtUserInfo createdBy;

    @Schema(description = "User roles")
    @NotEmpty(message = "Roles should not be empty")
    private List<RoleDto> roles;
}
