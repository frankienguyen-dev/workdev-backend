package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyResponse;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Update Job DTO request")
public class UpdateJobDto {
    @Schema(description = "Job Id")
    private String id;

    @Schema(description = "Job name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(description = "Job description")
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @Schema(description = "Job responsibility")
    @NotEmpty(message = "Responsibility should not be empty")
    private String responsibility;

    @Schema(description = "Job location")
    @NotEmpty(message = "Location should not be empty")
    private String location;

    @Schema(description = "Job quantity")
    @NotNull(message = "Quantity should not be empty")
    private Integer quantity;

    @Schema(description = "Job salary")
    @NotNull(message = "Salary should not be empty")
    private Long salary;

    @Schema(description = "Job education")
    @NotEmpty(message = "Education should not be empty")
    private String education;

    @Schema(description = "Job type")
    @NotEmpty(message = "Job type should not be empty")
    private String jobType;

    @Schema(description = "Job experience")
    @NotEmpty(message = "Experience should not be empty")
    private String experience;

    @Schema(description = "Job category")
    @NotNull(message = "Category should not be empty")
    @Valid
    private CategoryInfo category;

    @Schema(description = "Job level")
    @NotEmpty(message = "Level should not be empty")
    private String level;

    @Schema(description = "Job updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Job updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Job company")
    @Valid
    @NotNull(message = "Company should not be empty")
    private CompanyResponse company;

    @Schema(description = "Job start date")
    @NotNull(message = "Start date should not be empty")
    private LocalDateTime startDate;

    @Schema(description = "Job end date")
    @NotNull(message = "End date should not be empty")
    private LocalDateTime endDate;

    @Schema(description = "Job skills")
    @NotNull(message = "Skills should not be empty")
    @Valid
    private List<SkillDto> skills;

    @Schema(description = "Job status")
    @NotNull(message = "Job status is required")
    private Boolean isActive;
}
