package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
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
@Schema(description = "Update resume DTO request")
public class UpdateResumeDto {

    @Schema(description = "Resume Id")
    private String id;

    @Schema(description = "Resume status")
    @NotEmpty(message = "Resume should not be empty")
    private String status;

    @Schema(description = "Resume updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Resume updated at")
    private LocalDateTime updatedAt;
}
