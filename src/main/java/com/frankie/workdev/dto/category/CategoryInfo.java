package com.frankie.workdev.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo {
    @NotEmpty(message = "Category id is required")
    private String id;
    private String name;
    private String description;
}
