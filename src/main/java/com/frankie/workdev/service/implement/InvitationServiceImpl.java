package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.dto.invitation.*;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.Invitation;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ApiException;
import com.frankie.workdev.exception.MyNullPointerException;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.InvitationRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.InvitationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<InvitationResponse> createInvitation(InvitationDto invitationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        User senderUser = userRepository.findByEmail(getUserEmail);

        if (senderUser.getCompany() == null) {
            throw new MyNullPointerException("User", "company", "null");
        }

        Company companySenderUser = companyRepository.findByName(
                senderUser.getCompany().getName()
        );
        User receiverUser = userRepository.findByEmail(invitationDto.getReceiverUserEmail());
        if (receiverUser == null) {
            throw new ResourceNotFoundException("User", "id",
                    invitationDto.getReceiverUserEmail());
        }
        if (receiverUser.getCompany() != null) {
            if (receiverUser.getCompany().getName().equalsIgnoreCase(companySenderUser.getName())) {
                throw new ResourceExistingException(
                        "User",
                        "company",
                        receiverUser.getCompany().getName()
                );
            }
            throw new ApiException(HttpStatus.BAD_REQUEST, "User already exists company");
        }
//        boolean existsInvitationByReceiverUser = invitationRepository
//                .existsInvitationByReceiverUser(receiverUser);
//        if (existsInvitationByReceiverUser) {
//            throw new ResourceExistingException(
//                    "Invitation",
//                    "receiverUser",
//                    invitationDto.getReceiverUserEmail()
//            );
//        }
        Invitation invitation = new Invitation();
        invitation.setCompany(companySenderUser);
        invitation.setContent(invitationDto.getContent());
        invitation.setReceiverUser(receiverUser);
        invitation.setSenderUser(senderUser);
        invitation.setStatus("PENDING");
        invitation.setCreatedAt(LocalDateTime.now());
        Invitation savedInvitation = invitationRepository.save(invitation);
        JwtUserInfo getSenderUser = new JwtUserInfo();
        getSenderUser.setId(getUserId);
        getSenderUser.setEmail(getUserEmail);
        JwtUserInfo getReceiverUser = new JwtUserInfo();
        getReceiverUser.setId(receiverUser.getId());
        getReceiverUser.setEmail(receiverUser.getEmail());
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setId(savedInvitation.getCompany().getId());
        companyInfo.setName(savedInvitation.getCompany().getName());
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setId(savedInvitation.getId());
        invitationResponse.setSenderUser(getSenderUser);
        invitationResponse.setCompany(companyInfo);
        invitationResponse.setReceiverUser(getReceiverUser);
        invitationResponse.setContent(savedInvitation.getContent());
        invitationResponse.setStatus(savedInvitation.getStatus());
        invitationResponse.setCreatedAt(savedInvitation.getCreatedAt());
        return ApiResponse.success(
                "Invitation join company success",
                HttpStatus.OK,
                invitationResponse
        );
    }

    @Override
    public ApiResponse<InvitationInfo> getInvitationOfUser(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            throw new ResourceNotFoundException("User", "email", email);
        }
        List<Invitation> invitation = invitationRepository.findAllByReceiverUser(findUser);
        List<InvitationResponse> invitationResponse = invitation.stream()
                .map(invite -> {
                    try {
                        return modelMapper.map(invite, InvitationResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        InvitationInfo invitationInfo = new InvitationInfo();
        invitationInfo.setInvitations(invitationResponse);

        return ApiResponse.success(
                "Get invitation of user success",
                HttpStatus.OK,
                invitationInfo
        );

    }

    @Override
    public ApiResponse<AcceptOrRejectResponse> acceptOrRejectInvitation(
            String id, AcceptOrRejectRequest acceptOrRejectRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String getUserEmail = authentication.getName();
            User currentUSer = userRepository.findByEmail(getUserEmail);
            Invitation findInvitation = invitationRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Invitation", "id", id)
            );

            User receiverUser = findInvitation.getReceiverUser();
            if (receiverUser.getCompany() != null) {
                throw new ResourceExistingException(
                        "User",
                        "company",
                        receiverUser.getCompany().getName()
                );
            }
            if (!findInvitation.getStatus().equalsIgnoreCase("PENDING")) {
                throw new ResourceExistingException(
                        "Invitation",
                        "status",
                        findInvitation.getStatus()
                );
            }
            if (currentUSer == receiverUser) {
                if (acceptOrRejectRequest.isAccepted()) {
                    findInvitation.setStatus("ACCEPTED");
                    receiverUser.setCompany(findInvitation.getCompany());
                    findInvitation.setAcceptedAt(LocalDateTime.now());
                } else {
                    findInvitation.setStatus("REJECTED");
                    receiverUser.setCompany(null);
                    findInvitation.setRejectedAt(LocalDateTime.now());
                }
                Invitation save = invitationRepository.save(findInvitation);
                AcceptOrRejectResponse acceptOrRejectResponse = new AcceptOrRejectResponse();
                acceptOrRejectResponse.setMessage("Invitation " +
                        (acceptOrRejectRequest.isAccepted() ? "accepted" : "rejected")
                        + " successfully");
                acceptOrRejectResponse.setStatus(save.getStatus());
                acceptOrRejectResponse.setAcceptedAt(save.getAcceptedAt());
                acceptOrRejectResponse.setRejectedAt(save.getRejectedAt());
                return ApiResponse.success(
                        "Accept or reject invitation success",
                        HttpStatus.OK,
                        acceptOrRejectResponse
                );
            } else {
                return ApiResponse.error(
                        "You are not receiver user",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<InvitationListResponse> getAllInvitations(int pageNo, int pageSize,
                                                                 String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Invitation> invitations = invitationRepository.findAll(pageable);
        List<Invitation> invitationContentList = invitations.getContent();
        List<InvitationResponse> invitationInfoDtoList = invitationContentList.stream()
                .map(invitation -> {
                    try {
                        return modelMapper.map(invitation, InvitationResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(invitations.getTotalPages());
        metaData.setTotalElements(invitations.getTotalElements());
        metaData.setPageSize(invitations.getSize());
        metaData.setLastPage(invitations.isLast());
        metaData.setPageNo(invitations.getNumber());
        InvitationListResponse invitationListResponse = new InvitationListResponse();
        invitationListResponse.setData(invitationInfoDtoList);
        invitationListResponse.setMeta(metaData);
        return ApiResponse.success(
                "Get all invitations success",
                HttpStatus.OK,
                invitationListResponse
        );
    }

    @Override
    public ApiResponse<InvitationListResponse> searchInvitation(String email, int pageNo,
                                                                int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Invitation> invitations = invitationRepository.searchInvitationByEmail(email, pageable);
        List<Invitation> invitationContentList = invitations.getContent();
        List<InvitationResponse> invitationInfoDtoList = invitationContentList.stream()
                .map(invitation -> {
                    try {
                        return modelMapper.map(invitation, InvitationResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(invitations.getTotalPages());
        metaData.setTotalElements(invitations.getTotalElements());
        metaData.setPageSize(invitations.getSize());
        metaData.setLastPage(invitations.isLast());
        metaData.setPageNo(invitations.getNumber());
        InvitationListResponse invitationListResponse = new InvitationListResponse();
        invitationListResponse.setData(invitationInfoDtoList);
        invitationListResponse.setMeta(metaData);
        return ApiResponse.success(
                "Search invitation success",
                HttpStatus.OK,
                invitationListResponse
        );
    }
}