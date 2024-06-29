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
@Schema(description = "Company Info Response")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfoResponse {
    @Schema(description = "Company Id")
    private String id;

    @Schema(description = "Company Name")
    private String name;
}
