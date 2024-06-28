package com.frankie.workdev.dto.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Company Info DTO")
public class CompanyInfo {
    @Schema(description = "Company Id", hidden = true)
    private String id;

    @Schema(description = "Company Name")
    private String name;
}
