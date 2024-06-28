package com.frankie.workdev.dto.apiResponse;

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
@Schema(description = "Error details")
public class ErrorDetails {
    @Schema(description = "Error timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Error details")
    private String details;
}
