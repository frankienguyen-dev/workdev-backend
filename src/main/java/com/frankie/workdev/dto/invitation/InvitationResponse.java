package com.frankie.workdev.dto.invitation;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.user.JwtUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationResponse {
    private String id;
    private JwtUserInfo senderUser;
    private JwtUserInfo receiverUser;
    private CompanyInfo company;
    private String status;
    private String content;
    private LocalDateTime createdAt;
}
