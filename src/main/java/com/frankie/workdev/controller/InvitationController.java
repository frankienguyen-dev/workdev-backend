package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.invitation.*;
import com.frankie.workdev.service.InvitationService;
import com.frankie.workdev.util.AppConstants;
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

    @GetMapping("/get-invitation")
    public ResponseEntity<ApiResponse<InvitationInfo>> getAllInvitationOfUser(
            @RequestParam(value = "email", required = false) String email) {
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

    @GetMapping
    public ResponseEntity<ApiResponse<InvitationListResponse>> getAllInvitations(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<InvitationListResponse> getAllInvitations = invitationService
                .getAllInvitations(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllInvitations, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<InvitationListResponse>> searchInvitation(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<InvitationListResponse> searchInvitation = invitationService
                .searchInvitation(email, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchInvitation, HttpStatus.OK);
    }
}
