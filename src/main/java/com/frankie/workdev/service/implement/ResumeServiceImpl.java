package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.resume.*;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.*;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.*;
import com.frankie.workdev.service.ResumeService;
import com.frankie.workdev.util.UserInfoUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreateResumeResponse> createResume(CreateResumeDto createResumeDto) {
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User createdByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Company findCompany = companyRepository.findByName(createResumeDto.getCompany().getName());
        if(findCompany == null) {
            throw new ResourceNotFoundException("Company", "name",
                    createResumeDto.getCompany().getName());
        }
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
        CreateResumeResponse createResumeResponse = modelMapper
                .map(saveResume, CreateResumeResponse.class);
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
    public ApiResponse<ResumeListResponse> getAllResumes(int pageNo, int pageSize,
                                                         String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.findAll(pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeResponse> resumeInfoDtoList = resumeContentList.stream()
                .map(resume -> {
                    try {
//                        User user = resume.getUser();
//                        JwtUserInfo jwtUserInfo = new JwtUserInfo();
//                        jwtUserInfo.setId(user.getId());
//                        jwtUserInfo.setEmail(user.getEmail());
//                        ResumeInfoDto resumeInfoDto = modelMapper.map(resume, ResumeInfoDto.class);
//                        resumeInfoDto.setUser(jwtUserInfo);
//                        return resumeInfoDto;

                        return modelMapper.map(resume, ResumeResponse.class);
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
        ResumeListResponse resumeResponse = new ResumeListResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetch all resumes successfully",
                HttpStatus.OK,
                resumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeResponse> getResumeById(String id) {
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        ResumeResponse resumeInfoDto = modelMapper.map(findResume, ResumeResponse.class);
        return ApiResponse.success(
                "Fetch resume successfully",
                HttpStatus.OK,
                resumeInfoDto
        );
    }

    @Override
    public ApiResponse<UpdateResumeResponse> updateResumeById(String id, UpdateResumeDto updateResumeDto) {
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User updatedByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        findResume.setStatus(updateResumeDto.getStatus());
        findResume.setUpdatedBy(updatedByUser);
        findResume.setUpdatedAt(LocalDateTime.now());
        Resume saveUpdate = resumeRepository.save(findResume);
        UpdateResumeResponse updateResumeResponse = modelMapper
                .map(saveUpdate, UpdateResumeResponse.class);
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
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User deletedByUser = userRepository.findByEmail(getUserInfo.getEmail());
        Resume findResume = resumeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resume", "id", id)
        );
        findResume.setIsDeleted(true);
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
    public ApiResponse<ResumeListResponse> getResumeByUser(int pageNo, int pageSize,
                                                           String sortBy, String sortDir) {
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User user = userRepository.findByEmail(getUserInfo.getEmail());
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.findResumeByUserId(user.getId(), pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeResponse> resumeInfoDtoList = resumeContentList.stream()
                .map(resume -> {
                    try {
                        return modelMapper.map(resume, ResumeResponse.class);
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
        ResumeListResponse resumeResponse = new ResumeListResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetch resume successfully",
                HttpStatus.OK,
                resumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeListResponse> searchResumeByEmail
            (String email, int pageNo, int pageSize,
             String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.searchResumeByEmail(email, pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeResponse> resumeInfoDtoList = resumeContentList.stream().map(
                resume -> {
                    try {
                        return modelMapper.map(resume, ResumeResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(resumes.getTotalPages());
        metaData.setTotalElements(resumes.getTotalElements());
        metaData.setPageSize(resumes.getSize());
        metaData.setPageNo(resumes.getNumber());
        metaData.setLastPage(resumes.isLast());
        ResumeListResponse resumeResponse = new ResumeListResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Search resume successfully!",
                HttpStatus.OK,
                resumeResponse
        );
    }

    @Override
    public ApiResponse<ResumeListResponse> getAllResumesByJobId(String jobId, int pageNo, int pageSize,
                                                                String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Resume> resumes = resumeRepository.getAllResumeByJobId(jobId, pageable);
        List<Resume> resumeContentList = resumes.getContent();
        List<ResumeResponse> resumeInfoDtoList = resumeContentList.stream().map(
                resume -> {
                    try {
                        return modelMapper.map(resume, ResumeResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
        ).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setTotalPages(resumes.getTotalPages());
        metaData.setTotalElements(resumes.getTotalElements());
        metaData.setPageSize(resumes.getSize());
        metaData.setPageNo(resumes.getNumber());
        metaData.setLastPage(resumes.isLast());
        ResumeListResponse resumeResponse = new ResumeListResponse();
        resumeResponse.setData(resumeInfoDtoList);
        resumeResponse.setMeta(metaData);
        return ApiResponse.success(
                "Fetch resume successfully",
                HttpStatus.OK,
                resumeResponse
        );
    }
}
