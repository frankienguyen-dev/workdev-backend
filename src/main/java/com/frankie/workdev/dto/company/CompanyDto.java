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
public class CompanyDto {
    private String id;
    private String name;
    private String description;
    private String address;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private boolean isDeleted;
}
