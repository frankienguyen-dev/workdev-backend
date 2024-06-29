package com.frankie.workdev.dto.job;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobId {
    @NotEmpty(message = "Job Id should not be empty")
    private String id;
}
