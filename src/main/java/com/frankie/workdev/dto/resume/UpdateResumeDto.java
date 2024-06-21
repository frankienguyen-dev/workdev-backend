package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.user.JwtUserInfo;
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
public class UpdateResumeDto {
    private String id;

    @NotEmpty(message = "Resume should not be empty")
    private String status;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}
