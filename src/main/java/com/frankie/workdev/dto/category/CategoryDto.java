package com.frankie.workdev.dto.category;
import com.frankie.workdev.dto.job.JobInfo;
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
@Schema(description = "Category information DTO")
public class CategoryDto {

    @Schema(description = "Category id")
    private String id;

    @Schema(description = "Category name")
    private String name;

    @Schema(description = "Category description")
    private String description;

    @Schema(description = "List of jobs")
    private List<JobInfo> jobs;

    @Schema(description = "Category created by")
    private JwtUserInfo createdBy;

    @Schema(description = "Category created at")
    private LocalDateTime createdAt;

    @Schema(description = "Category updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Category updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Category deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Category deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Category is deleted")
    private Boolean isDeleted;
}
