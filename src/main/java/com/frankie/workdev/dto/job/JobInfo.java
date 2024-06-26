package com.frankie.workdev.dto.job;
import com.frankie.workdev.dto.category.CategoryInfoResponse;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
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
@Schema(description = "Job Info")
public class JobInfo extends BaseJob<CompanyInfoResponse, CategoryInfoResponse> {
    @Schema(description = "Job is deleted")
    private Boolean isDeleted;

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public CategoryInfoResponse getCategory() {
        return super.getCategory();
    }

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }
}
