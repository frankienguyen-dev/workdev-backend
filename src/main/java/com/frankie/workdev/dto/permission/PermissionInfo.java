package com.frankie.workdev.dto.permission;

import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionInfo {
    private String id;
    private String name;
    private String path;
    private String method;
    private String module;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
}
