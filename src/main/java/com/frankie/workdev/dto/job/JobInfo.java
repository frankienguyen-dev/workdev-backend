package com.frankie.workdev.dto.job;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Job Info")
public class JobInfo {

    @Schema(description = "Job Id")
    @NotEmpty(message = "Id should not be empty")
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

    @Schema(description = "Job start date")
    private LocalDateTime startDate;

    @Schema(description = "Job end date")
    private LocalDateTime endDate;

    @Schema(description = "Job status")
    private Boolean isActive;

    @Schema(description = "Job is deleted")
    private Boolean isDeleted;
}
