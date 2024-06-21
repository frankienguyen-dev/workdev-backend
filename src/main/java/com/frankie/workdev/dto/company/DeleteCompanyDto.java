package com.frankie.workdev.dto.company;

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
public class DeleteCompanyDto {
    private String id;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
}
