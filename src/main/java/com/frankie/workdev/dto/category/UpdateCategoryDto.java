package com.frankie.workdev.dto.category;

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
public class UpdateCategoryDto {
    private String id;
    private String name;
    private String description;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}