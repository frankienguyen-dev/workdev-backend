package com.frankie.workdev.dto.user.request;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.BaseUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Update User Dto Request")
public class UpdateUserDto extends BaseUser<FileUploadDto, RoleDto, CompanyInfo> {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @NotEmpty(message = "Full name should not be empty")
    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @NotEmpty(message = "Address should not be empty")
    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @NotEmpty(message = "Phone number should not be empty")
    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @NotEmpty(message = "Gender should not be empty")
    @Override
    public String getGender() {
        return super.getGender();
    }

    @NotEmpty(message = "Title should not be empty")
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @NotEmpty(message = "Education should not be empty")
    @Override
    public String getEducation() {
        return super.getEducation();
    }

    @NotEmpty(message = "Experience should not be empty")
    @Override
    public String getExperience() {
        return super.getExperience();
    }

    @NotNull(message = "Age should not be empty")
    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public FileUploadDto getAvatar() {
        return super.getAvatar();
    }

    @NotEmpty(message = "Roles should not be empty")
    @Override
    public List<RoleDto> getRoles() {
        return super.getRoles();
    }

    @NotNull(message = "Company should not be empty")
    @Override
    public CompanyInfo getCompany() {
        return super.getCompany();
    }

}
