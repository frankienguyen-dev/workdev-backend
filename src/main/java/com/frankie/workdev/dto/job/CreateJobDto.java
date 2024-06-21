package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
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
public class CreateJobDto {
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Responsibility is required")
    private String responsibility;

    @NotEmpty(message = "Location is required")
    private String location;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Salary is required")
    private Long salary;

    @NotEmpty(message = "Education is required")
    private String education;

    @NotEmpty(message = "Job type is required")
    private String jobType;

    @NotEmpty(message = "Experience is required")
    private String experience;

    @NotNull(message = "Category is required")
    @Valid
    private CategoryInfo category;

    private JwtUserInfo user;

    @NotEmpty(message = "Level is required")
    private String level;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;

    @Valid
    @NotNull(message = "Company is required")
    private CompanyDto company;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future or present")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDateTime endDate;

    @NotEmpty(message = "Skills is required")
    @Valid
    private List<SkillDto> skills;

    @NotNull(message = "Job status is required")
    private Boolean isActive;
}
