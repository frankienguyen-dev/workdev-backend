package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Delete Job DTO request")
public class DeleteJobDto {

    @Schema(description = "Job Id")
    private String id;

    @Schema(description = "Job deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Job deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Job is active")
    private Boolean isActive;

    @Schema(description = "Job is deleted")
    private Boolean isDeleted;
}
