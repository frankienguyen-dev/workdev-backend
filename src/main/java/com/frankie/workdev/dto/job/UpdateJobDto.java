package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
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
public class UpdateJobDto {
    private String id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "Responsibility should not be empty")
    private String responsibility;

    @NotEmpty(message = "Location should not be empty")
    private String location;

    @NotNull(message = "Quantity should not be empty")
    private Integer quantity;

    @NotNull(message = "Salary should not be empty")
    private Long salary;

    @NotEmpty(message = "Education should not be empty")
    private String education;

    @NotEmpty(message = "Job type should not be empty")
    private String jobType;

    @NotEmpty(message = "Experience should not be empty")
    private String experience;

    @NotNull(message = "Category should not be empty")
    @Valid
    private CategoryInfo category;

    @NotEmpty(message = "Level should not be empty")
    private String level;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;

    @Valid
    @NotNull(message = "Company should not be empty")
    private CompanyDto company;

    @NotNull(message = "Start date should not be empty")
    private LocalDateTime startDate;

    @NotNull(message = "End date should not be empty")
    private LocalDateTime endDate;

    @NotNull(message = "Skills should not be empty")
    @Valid
    private List<SkillDto> skills;

    @NotNull(message = "Job status is required")
    private Boolean isActive;
}
