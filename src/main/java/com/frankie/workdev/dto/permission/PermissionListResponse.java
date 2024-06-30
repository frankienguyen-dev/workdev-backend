package com.frankie.workdev.dto.permission;

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
@Schema(description = "Permission Response")
public class PermissionListResponse {
    @Schema(description = "Permission metadata")
    private MetaData meta;

    @Schema(description = "Permission data")
    private List<PermissionResponse> data;
}
