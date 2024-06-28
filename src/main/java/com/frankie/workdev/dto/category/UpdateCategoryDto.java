package com.frankie.workdev.dto.category;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Update category DTO request")
public class UpdateCategoryDto {
    @Schema(description = "Category Id")
    private String id;

    @Schema(description = "Category name")
    @NotEmpty(message = "Category name should not be empty")
    private String name;

    @Schema(description = "Category description")
    @NotEmpty(message = "Category description should not be empty")
    private String description;

    @Schema(description = "Category updated by")
    private JwtUserInfo updatedBy;

    @Schema(description = "Category updated at")
    private LocalDateTime updatedAt;
}
