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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update job response")
public class UpdateJobResponse extends BaseJob<CompanyInfoResponse, CategoryInfoResponse>{
    @Schema(description = "Job updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Job updated at")
    private LocalDateTime updatedAt;

    @Override
    public CategoryInfoResponse getCategory() {
        return super.getCategory();
    }

    @Override
    public CompanyInfoResponse getCompany() {
        return super.getCompany();
    }
}
