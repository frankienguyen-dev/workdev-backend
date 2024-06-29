package com.frankie.workdev.dto.user.request;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.request.RoleDto;
import com.frankie.workdev.dto.upload.BaseFileUpload;
import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.BaseUser;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Override
    public FileUploadDto getAvatar() {
        return super.getAvatar();
    }

    @Override
    public List<RoleDto> getRoles() {
        return super.getRoles();
    }

    @Override
    public CompanyInfo getCompany() {
        return super.getCompany();
    }
}
