package com.frankie.workdev.dto.category;

import com.frankie.workdev.dto.job.JobInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo {
    private String id;
    private String name;
    private String description;
}
