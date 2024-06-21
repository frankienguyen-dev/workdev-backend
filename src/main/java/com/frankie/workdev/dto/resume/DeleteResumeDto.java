package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.user.JwtUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResumeDto {
    private String id;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
}
