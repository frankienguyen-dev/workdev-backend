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
@Schema(description = "Company Info DTO")
public class CompanyInfo {
    @Schema(description = "Company id", hidden = true)
    private String id;
    @Schema(description = "Company name")
    @NotEmpty(message = "Company name should not be empty")
    private String name;
}
