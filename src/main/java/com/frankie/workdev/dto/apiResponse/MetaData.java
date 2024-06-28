package com.frankie.workdev.dto.apiResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Pagination MetaData")
public class MetaData {

    @Schema(description = "Page number")
    private int pageNo;

    @Schema(description = "Page size")
    private int pageSize;

    @Schema(description = "Total number of elements")
    private long totalElements;

    @Schema(description = "Total number of pages")
    private int totalPages;

    @Schema(description = "Is last page")
    private boolean lastPage;
}
