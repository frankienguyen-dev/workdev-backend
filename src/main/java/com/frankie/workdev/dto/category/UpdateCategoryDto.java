package com.frankie.workdev.dto.category;

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
public class UpdateCategoryDto {
    private String id;

    @NotEmpty(message = "Category name should not be empty")
    private String name;

    @NotEmpty(message = "Category description should not be empty")
    private String description;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
}
