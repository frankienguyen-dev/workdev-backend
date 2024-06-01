package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.category.CategoryDto;
import com.frankie.workdev.dto.category.CategoryInfo;
import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.resume.ResumeInfo;
import com.frankie.workdev.dto.resume.ResumeInfoDto;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
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
public class JobDto {
    private String id;
    private String name;
    private String description;
    private String responsibility;
    private String location;
    private int quantity;
    private Long salary;
    private String level;
    private String education;
    private String jobType;
    private String experience;
    private List<ResumeInfo> resumes;
    private CategoryInfo category;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private CompanyDto company;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<SkillDto> skills;
    private boolean isActive;
    private boolean isDeleted;
}
