package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.company.CompanyResponse;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create job DTO request")
public class CreateJobDto extends BaseJob<CompanyInfo, CategoryInfo> {

    @Schema(description = "Job user", hidden = true)
    private JwtUserInfo user;

    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getResponsibility() {
        return super.getResponsibility();
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public Integer getQuantity() {
        return super.getQuantity();
    }

    @Override
    public Long getSalary() {
        return super.getSalary();
    }

    @Override
    public String getEducation() {
        return super.getEducation();
    }

    @Override
    public String getJobType() {
        return super.getJobType();
    }

    @Override
    public String getExperience() {
        return super.getExperience();
    }

    @Override
    public CategoryInfo getCategory() {
        return super.getCategory();
    }

    @Override
    public String getLevel() {
        return super.getLevel();
    }

    @Override
    public CompanyInfo getCompany() {
        return super.getCompany();
    }

    @Override
    public LocalDateTime getStartDate() {
        return super.getStartDate();
    }

    @Override
    public LocalDateTime getEndDate() {
        return super.getEndDate();
    }

    @Override
    public List<SkillDto> getSkills() {
        return super.getSkills();
    }

    @NotNull(message = "Job status is required")
    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }
}
