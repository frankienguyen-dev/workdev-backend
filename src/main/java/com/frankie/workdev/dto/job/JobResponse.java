package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.apiResponse.MetaData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobResponse {
    private MetaData meta;
    private List<JobDto> data;
}
