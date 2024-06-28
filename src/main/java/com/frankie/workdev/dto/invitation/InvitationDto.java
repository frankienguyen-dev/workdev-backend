package com.frankie.workdev.dto.invitation;

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
@Schema(description = "Invitation Dto request")
public class InvitationDto {
    @Schema(description = "Invitation Id")
    private String id;

    @Schema(description = "Receiver user email")
    @NotEmpty(message = "receiverUserEmail is required")
    private String receiverUserEmail;

    @Schema(description = "Invitation content")
    @NotEmpty(message = "content is required")
    private String content;
}
