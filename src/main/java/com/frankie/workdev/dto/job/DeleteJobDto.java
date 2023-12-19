package com.frankie.workdev.dto.job;

import com.frankie.workdev.dto.user.JwtUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteJobDto {
    private String id;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private boolean isActive;
    private boolean isDeleted;
}
