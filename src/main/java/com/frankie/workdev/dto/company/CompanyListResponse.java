package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.apiResponse.MetaData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Company Response")
public class CompanyListResponse {

    @Schema(description = "Meta Data")
    private MetaData meta;

    @Schema(description = "Company Data")
    private List<CompanyResponse> data;
}
