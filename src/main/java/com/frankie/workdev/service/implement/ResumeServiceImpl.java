package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.resume.*;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.*;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.*;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.ResumeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private ResumeRepository resumeRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private JobRepository jobRepository;
    private FileRepository fileRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<CreateResumeDto> createResume(CreateResumeDto createResumeDto) {
        JwtUserInfo getUserInfo = getUserInfoFromToken();
        User createdByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Company findCompany = companyRepository.findById(createResumeDto.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id",
                        createResumeDto.getCompany().getId()));
        Job findJob = jobRepository.findById(createResumeDto.getJob().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id",
                        createResumeDto.getJob().getId()));
        Resume createResume = new Resume();
        createResume.setUrl(createResume.getUrl());
        createResume.setCompany(findCompany);
        createResume.setJob(findJob);
        createResume.setStatus("PENDING");
        createResume.setCreatedAt(LocalDateTime.now());
        createResume.setCreatedBy(createdByUser);
        FileEntity resume = fileRepository.findById(createResumeDto.getResume().getId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "id",
                        createResumeDto.getResume().getId()));
        createResume.setResume(resume);
        createResume.setUser(createdByUser);
        Resume saveResume = resumeRepository.save(createResume);
        CreateResumeDto createResumeResponse = modelMapper.map(saveResume, CreateResumeDto.class);
        createResumeResponse.setCreatedBy(getUserInfo);
        createResumeResponse.setUser(getUserInfo);
        createResumeResponse.setCreatedAt(saveResume.getCreatedAt());
        return ApiResponse.success(
                "Create new resume successfully",
                HttpStatus.CREATED,
                createResumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeResponse> getAllResumes(int pageNo, int pageSize,
                                                     String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.findAll(pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeInfoDto> resumeInfoDtoList = resumeContentList.stream()
                .map(resume -> {
                    try {
//                        User user = resume.getUser();
//                        JwtUserInfo jwtUserInfo = new JwtUserInfo();
//                        jwtUserInfo.setId(user.getId());
//                        jwtUserInfo.setEmail(user.getEmail());
//                        ResumeInfoDto resumeInfoDto = modelMapper.map(resume, ResumeInfoDto.class);
//                        resumeInfoDto.setUser(jwtUserInfo);
//                        return resumeInfoDto;

                        return modelMapper.map(resume, ResumeInfoDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(resumes.getTotalPages());
        metaData.setTotalElements(resumes.getTotalElements());
        metaData.setPageSize(resumes.getSize());
        metaData.setPageNo(resumes.getNumber());
        metaData.setLastPage(resumes.isLast());
        ResumeResponse resumeResponse = new ResumeResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetch all resumes successfully",
                HttpStatus.OK,
                resumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeInfoDto> getResumeById(String id) {
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        ResumeInfoDto resumeInfoDto = modelMapper.map(findResume, ResumeInfoDto.class);
        return ApiResponse.success(
                "Fetch resume successfully",
                HttpStatus.OK,
                resumeInfoDto
        );
    }

    @Override
    public ApiResponse<UpdateResumeDto> updateResumeById(String id, UpdateResumeDto updateResumeDto) {
        JwtUserInfo getUserInfo = getUserInfoFromToken();
        User updatedByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        findResume.setStatus(updateResumeDto.getStatus());
        findResume.setUpdatedBy(updatedByUser);
        findResume.setUpdatedAt(LocalDateTime.now());
        Resume saveUpdate = resumeRepository.save(findResume);
        UpdateResumeDto updateResumeResponse = modelMapper.map(saveUpdate, UpdateResumeDto.class);
        updateResumeResponse.setUpdatedBy(getUserInfo);
        updateResumeResponse.setUpdatedAt(saveUpdate.getUpdatedAt());
        return ApiResponse.success(
                "Update resume successfully",
                HttpStatus.OK,
                updateResumeResponse
        );
    }

    @Override
    public ApiResponse<DeleteResumeDto> deleteResumeById(String id) {
        JwtUserInfo getUserInfo = getUserInfoFromToken();
        User deletedByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        findResume.setDeleted(true);
        findResume.setDeletedBy(deletedByUser);
        findResume.setDeletedAt(LocalDateTime.now());
        Resume saveDelete = resumeRepository.save(findResume);
        DeleteResumeDto deleteResumeResponse = modelMapper.map(saveDelete, DeleteResumeDto.class);
        deleteResumeResponse.setDeletedBy(getUserInfo);
        deleteResumeResponse.setDeletedAt(saveDelete.getDeletedAt());
        return ApiResponse.success(
                "Delete resume successfully",
                HttpStatus.OK,
                deleteResumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeResponse> getResumeByUser(int pageNo, int pageSize,
                                                       String sortBy, String sortDir) {
        JwtUserInfo getUserInfo = getUserInfoFromToken();
        User user = userRepository.findByEmail(getUserInfo.getEmail());
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.findByCreatedBy(user, pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeInfoDto> resumeInfoDtoList = resumeContentList.stream()
                .map(resume -> {
                    try {
                        return modelMapper.map(resume, ResumeInfoDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;

                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(resumes.getTotalPages());
        metaData.setTotalElements(resumes.getTotalElements());
        metaData.setPageSize(resumes.getSize());
        metaData.setPageNo(resumes.getNumber());
        metaData.setLastPage(resumes.isLast());
        ResumeResponse resumeResponse = new ResumeResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetch resume successfully",
                HttpStatus.OK,
                resumeResponse
        );
    }

    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }
}
