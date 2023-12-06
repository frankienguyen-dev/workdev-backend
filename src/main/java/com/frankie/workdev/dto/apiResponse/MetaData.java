package com.frankie.workdev.dto.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
