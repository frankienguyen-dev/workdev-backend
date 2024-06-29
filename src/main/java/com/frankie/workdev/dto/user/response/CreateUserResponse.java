package com.frankie.workdev.dto.user.response;


import com.frankie.workdev.dto.company.CompanyInfoResponse;
import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.role.response.RoleResponse;
import com.frankie.workdev.dto.upload.UploadFileResponse;
import com.frankie.workdev.dto.user.BaseUser;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create User DTO Response")
public class CreateUserResponse extends BaseUser<UploadFileResponse, RoleResponse> {
    @Schema(description = "User company")
    private CompanyInfoResponse company;

    @Schema(description = "User created by")
    private JwtUserInfo createdBy;

    @Schema(description = "User created at")
    private LocalDateTime createdAt;
}
