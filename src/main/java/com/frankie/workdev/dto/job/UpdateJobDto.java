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
public class UpdateJobDto {
    private String id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "Location should not be empty")
    private String location;

    @NotNull(message = "Quantity should not be empty")
    private Integer quantity;

    @NotNull(message = "Salary should not be empty")
    private Long salary;

    @NotEmpty(message = "Level should not be empty")
    private String level;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;

    @Valid
    private CompanyDto company;

    @NotNull(message = "Start date should not be empty")
    private LocalDateTime startDate;

    @NotNull(message = "End date should not be empty")
    private LocalDateTime endDate;

    @Valid
    private List<SkillDto> skills;
    private boolean isActive;
}
