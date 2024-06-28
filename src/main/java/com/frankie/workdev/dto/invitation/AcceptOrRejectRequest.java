package com.frankie.workdev.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Accept or reject request")
public class AcceptOrRejectRequest {

    @Schema(description = "Accepted or rejected")
    @NotNull(message = "Accepted should not be empty")
    private Boolean accepted;
}
