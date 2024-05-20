package com.frankie.workdev.dto.category;

import com.frankie.workdev.dto.job.JobDto;
import com.frankie.workdev.dto.job.JobInfo;
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
public class CategoryDto {
    private String id;
    private String name;
    private String description;
    private List<JobInfo> jobs;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private boolean isDeleted;
}
