package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Company response")
public class CompanyResponse extends BaseCompany<FileUploadDto> {
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
