package com.frankie.workdev.dto.category;

import com.frankie.workdev.dto.apiResponse.MetaData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category response")
public class CategoryListResponse {

    @Schema(description = "Category metadata")
    private MetaData meta;

    @Schema(description = "Category data")
    private List<CategoryResponse> data;
}
