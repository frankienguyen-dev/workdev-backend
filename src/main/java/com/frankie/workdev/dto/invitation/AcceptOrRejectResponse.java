package com.frankie.workdev.dto.invitation;

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
@Schema(description = "Accept or reject response")
public class AcceptOrRejectResponse {

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Response status")
    private String status;

    @Schema(description = "Accepted at")
    private LocalDateTime acceptedAt;

    @Schema(description = "Rejected at")
    private LocalDateTime rejectedAt;
}
