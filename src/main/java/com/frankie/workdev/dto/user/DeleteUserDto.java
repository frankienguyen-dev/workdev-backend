package com.frankie.workdev.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {
    private String id;
    private LocalDateTime deletedAt;
    private JwtUserInfo deletedBy;
    private Boolean isDeleted;
}
