package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.apiResponse.MetaData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private MetaData meta;
    private List<CompanyDto> data;
}
