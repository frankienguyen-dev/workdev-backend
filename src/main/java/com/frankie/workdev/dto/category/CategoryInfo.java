package com.frankie.workdev.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category info")
public class CategoryInfo {

    @Schema(description = "Category id")
    @NotEmpty(message = "Category id is required")
    private String id;

    @Schema(description = "Category name", hidden = true)
    private String name;

    @Schema(description = "Category description", hidden = true)
    private String description;
}
