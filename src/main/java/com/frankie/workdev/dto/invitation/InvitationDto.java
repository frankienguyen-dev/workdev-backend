package com.frankie.workdev.dto.invitation;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {
    private String id;
    @NotEmpty(message = "receiverUserEmail is required")
    private String receiverUserEmail;
    private String content;
}
