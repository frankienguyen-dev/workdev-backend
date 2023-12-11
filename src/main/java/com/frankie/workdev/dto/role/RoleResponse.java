package com.frankie.workdev.dto.role;

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
public class RoleResponse {
    private MetaData meta;
    private List<RoleDto> data;
}
