package com.frankie.workdev.dto.user.request;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.dto.user.BaseUser;
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
public class UserDto extends BaseUser<FileUploadDto, RoleDto> {
    @Schema(description = "User company")
    private CompanyInfo company;

    @Schema(description = "User password")
    private String password;

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getEducation() {
        return super.getEducation();
    }

    @Override
    public String getExperience() {
        return super.getExperience();
    }

    @Override
    public FileUploadDto getAvatar() {
        return super.getAvatar();
    }

    @Override
    public String getBiography() {
        return super.getBiography();
    }

    @Override
    public String getCoverLetter() {
        return super.getCoverLetter();
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public List<RoleDto> getRoles() {
        return super.getRoles();
    }
}
