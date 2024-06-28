package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.apiResponse.MetaData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Job Response")
public class JobResponse {

    @Schema(description = "Job metadata")
    private MetaData meta;

    @Schema(description = "Job data")
    private List<JobDto> data;
}
