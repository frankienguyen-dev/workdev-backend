package com.frankie.workdev.dto.invitation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrRejectRequest {
    @NotNull(message = "Accepted should not be empty")
    private Boolean accepted;
}
