package com.frankie.workdev.dto.resume;

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
@Schema(description = "Resume Response")
public class ResumeListResponse {
    @Schema(description = "Resume metadata")
    private MetaData meta;

    @Schema(description = "Resume data")
    private List<ResumeResponse> data;
}
