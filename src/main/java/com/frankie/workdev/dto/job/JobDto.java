package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.resume.ResumeInfo;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Job DTO")
public class JobDto {

    @Schema(description = "Job Id")
    private String id;

    @Schema(description = "Job name")
    private String name;

    @Schema(description = "Job description")
    private String description;

    @Schema(description = "Job responsibility")
    private String responsibility;

    @Schema(description = "Job location")
    private String location;

    @Schema(description = "Job quantity")
    private int quantity;

    @Schema(description = "Job salary")
    private Long salary;

    @Schema(description = "Job level")
    private String level;

    @Schema(description = "Job education")
    private String education;

    @Schema(description = "Job type")
    private String jobType;

    @Schema(description = "Job experience")
    private String experience;

    @Schema(description = "Job resumes")
    private List<ResumeInfo> resumes;

    @Schema(description = "Job category")
    private CategoryInfo category;

    @Schema(description = "Job created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Job created at")
    private LocalDateTime createdAt;

    @Schema(description = "Job updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Job updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Job deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Job deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Job company")
    private CompanyDto company;

    @Schema(description = "Job start date")
    private LocalDateTime startDate;

    @Schema(description = "Job end date")
    private LocalDateTime endDate;

    @Schema(description = "Job skills")
    private List<SkillDto> skills;

    @Schema(description = "Job is active")
    private Boolean isActive;

    @Schema(description = "Job is deleted")
    private Boolean isDeleted;
}
