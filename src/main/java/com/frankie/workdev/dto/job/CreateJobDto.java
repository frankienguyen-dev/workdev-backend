package com.frankie.workdev.dto.job;

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
public class CreateJobDto {
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;
    private String responsibility;

    @NotEmpty(message = "Location is required")
    private String location;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Salary is required")
    private Long salary;
    private String education;
    private String jobType;
    private String experience;

    @NotEmpty(message = "Level is required")
    private String level;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;

    @Valid
    private CompanyDto company;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotEmpty(message = "Skills is required")
    private List<SkillDto> skills;
    private boolean isActive;
}
