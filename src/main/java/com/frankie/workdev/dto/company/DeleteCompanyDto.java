package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Delete Company DTO Request")
public class DeleteCompanyDto {

    @Schema(description = "Company Id")
    private String id;

    @Schema(description = "Company Deleted By")
    private JwtUserInfo deletedBy;

    @Schema(description = "Company Deleted At")
    private LocalDateTime deletedAt;

    @Schema(description = "Company Is Deleted")
    private Boolean isDeleted;
}
