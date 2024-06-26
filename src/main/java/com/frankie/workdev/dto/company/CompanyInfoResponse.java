package com.frankie.workdev.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
