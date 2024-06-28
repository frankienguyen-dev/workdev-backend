package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyDto;
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
public class CreateJobDto {
    @Schema(description = "Job Id")
    private String id;

    @Schema(description = "Job name")
    @NotEmpty(message = "Name is required")
    private String name;

    @Schema(description = "Job description")
    @NotEmpty(message = "Description is required")
    private String description;

    @Schema(description = "Job responsibility")
    @NotEmpty(message = "Responsibility is required")
    private String responsibility;

    @Schema(description = "Job location")
    @NotEmpty(message = "Location is required")
    private String location;

    @Schema(description = "Job quantity")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Schema(description = "Job salary")
    @NotNull(message = "Salary is required")
    private Long salary;

    @Schema(description = "Job education")
    @NotEmpty(message = "Education is required")
    private String education;

    @Schema(description = "Job type")
    @NotEmpty(message = "Job type is required")
    private String jobType;

    @Schema(description = "Job experience")
    @NotEmpty(message = "Experience is required")
    private String experience;

    @Schema(description = "Job category")
    @NotNull(message = "Category is required")
    @Valid
    private CategoryInfo category;

    @Schema(description = "Job user")
    private JwtUserInfo user;

    @Schema(description = "Job level")
    @NotEmpty(message = "Level is required")
    private String level;

    @Schema(description = "Job created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Job created at")
    private LocalDateTime createdAt;

    @Schema(description = "Job company")
    @Valid
    @NotNull(message = "Company is required")
    private CompanyDto company;

    @Schema(description = "Job start date")
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future or present")
    private LocalDateTime startDate;

    @Schema(description = "Job end date")
    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDateTime endDate;

    @Schema(description = "Job skills")
    @NotEmpty(message = "Skills is required")
    @Valid
    private List<SkillDto> skills;

    @Schema(description = "Job status")
    @NotNull(message = "Job status is required")
    private Boolean isActive;
}
