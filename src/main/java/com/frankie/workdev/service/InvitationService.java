package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.invitation.*;

import java.util.List;

public interface InvitationService {
    ApiResponse<InvitationResponse> createInvitation(InvitationDto invitationDto);

    ApiResponse<InvitationInfo> getInvitationOfUser(String email);

    ApiResponse<AcceptOrRejectResponse> acceptOrRejectInvitation(
            String id, AcceptOrRejectRequest acceptOrRejectRequest);

    ApiResponse<InvitationListResponse> getAllInvitations(int pageNo, int pageSize,
                                                          String sortBy, String sortDir);

    ApiResponse<InvitationListResponse> searchInvitation(String email, int pageNo, int pageSize,
                                                         String sortBy, String sortDir);
}
