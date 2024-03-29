package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.company.CompanyDto;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobInfo {
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private boolean isDeleted;
}
