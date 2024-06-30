package com.frankie.workdev.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category information response")
public class CategoryInfoResponse {
    @Schema(description = "Category id")
    private String id;

    @Schema(description = "Category name")
    private String name;
}
