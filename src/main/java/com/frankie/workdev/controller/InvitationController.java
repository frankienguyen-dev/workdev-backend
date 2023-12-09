package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.invitation.*;
import com.frankie.workdev.service.InvitationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/invitations")
public class InvitationController {

    private InvitationService invitationService;

    @PostMapping
    public ResponseEntity<ApiResponse<InvitationResponse>> invitationCompany(
            @RequestBody @Valid InvitationDto invitationDto
    ) {
        ApiResponse<InvitationResponse> invitation = invitationService
                .createInvitation(invitationDto);
        return new ResponseEntity<>(invitation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<InvitationInfo>> getAllInvitationOfUser(
            @RequestParam String email) {
        ApiResponse<InvitationInfo> getAllInvitationOfUser = invitationService
                .getInvitationOfUser(email);
        return new ResponseEntity<>(getAllInvitationOfUser, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<AcceptOrRejectResponse>> acceptOrRejectInvitation(
            @PathVariable("id") String id,
            @RequestBody AcceptOrRejectRequest acceptOrRejectRequest
            ) {
        ApiResponse<AcceptOrRejectResponse> acceptOrRejectInvitation = invitationService
                .acceptOrRejectInvitation(id, acceptOrRejectRequest);
        return new ResponseEntity<>(acceptOrRejectInvitation, HttpStatus.OK);
    }
}
