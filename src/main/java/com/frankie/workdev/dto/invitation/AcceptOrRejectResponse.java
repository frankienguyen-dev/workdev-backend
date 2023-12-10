package com.frankie.workdev.dto.invitation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrRejectResponse {
    private String message;
    private String status;
    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
}
