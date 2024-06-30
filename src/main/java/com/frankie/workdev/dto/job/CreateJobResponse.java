package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfoResponse;
import com.frankie.workdev.dto.company.CompanyInfoResponse;
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
@Schema(description = "Create job response")
public class CreateJobResponse extends BaseJob<CompanyInfoResponse, CategoryInfoResponse> {
    @Schema(description = "Job created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Job created at")
    private LocalDateTime createdAt;

    @Override
    public CategoryInfoResponse getCategory() {
        return super.getCategory();
    }

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }
}
