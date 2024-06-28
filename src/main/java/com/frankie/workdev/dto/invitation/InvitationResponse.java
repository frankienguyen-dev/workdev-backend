package com.frankie.workdev.dto.invitation;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Invitation Response")
public class InvitationResponse {

    @Schema(description = "Invitation id")
    private String id;

    @Schema(description = "Sender user email")
    private JwtUserInfo senderUser;

    @Schema(description = "Receiver user email")
    private JwtUserInfo receiverUser;

    @Schema(description = "Company info")
    private CompanyInfo company;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "Content")
    private String content;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Accepted at")
    private LocalDateTime acceptedAt;

    @Schema(description = "Rejected at")
    private LocalDateTime rejectedAt;
}
