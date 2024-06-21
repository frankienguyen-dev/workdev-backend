package com.frankie.workdev.dto.job;
import com.frankie.workdev.dto.category.CategoryDto;
import com.frankie.workdev.dto.category.CategoryInfo;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Id should not be empty")
    private String id;
    private String name;
    private String description;
    private String responsibility;
    private String location;
    private int quantity;
    private Long salary;
//    private CategoryDto category;
    private String level;
    private String education;
    private String jobType;
    private String experience;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Boolean isDeleted;
}
