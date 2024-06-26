package com.frankie.workdev.dto.job;

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
@Schema(description = "Job Response")
public class JobListResponse {

    @Schema(description = "Job metadata")
    private MetaData meta;

    @Schema(description = "Job data")
    private List<JobResponse> data;
}
