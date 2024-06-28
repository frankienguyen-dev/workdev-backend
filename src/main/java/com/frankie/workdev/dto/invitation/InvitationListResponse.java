package com.frankie.workdev.dto.invitation;

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
@Schema(description = "Invitation list response")
public class InvitationListResponse {
    @Schema(description = "Invitation metadata")
    private MetaData meta;

    @Schema(description = "Invitation data")
    private List<InvitationResponse> data;
}
