package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.job.*;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.Job;
import com.frankie.workdev.entity.Skill;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ApiException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.JobRepository;
import com.frankie.workdev.repository.SkillRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.JobService;
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
public class JobServiceImpl implements JobService {

    private SkillRepository skillRepository;
    private JobRepository jobRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<CreateJobDto> createJob(CreateJobDto createJobDto) {
        try {
            JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
            User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            if (createdByUser.getCompany() == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
            }
            Company findCompany = companyRepository.findByName(createJobDto
                    .getCompany().getName());
            if (findCompany == null) {
                throw new ResourceNotFoundException("Company", "name", createJobDto.getCompany()
                        .getName());
            }
            Job newJob = new Job();
            newJob.setName(createJobDto.getName());
            newJob.setDescription(createJobDto.getDescription());
            newJob.setLocation(createJobDto.getLocation());
            newJob.setQuantity(createJobDto.getQuantity());
            newJob.setSalary(createJobDto.getSalary());
            newJob.setLevel(createJobDto.getLevel());
            newJob.setCreatedBy(createdByUser);
            newJob.setCreatedAt(LocalDateTime.now());
            newJob.setCompany(findCompany);
            newJob.setStartDate(createJobDto.getStartDate());
            newJob.setEndDate(createJobDto.getEndDate());
            newJob.setActive(true);
            List<Skill> skills = getSkills(createJobDto.getSkills());
            newJob.setSkills(skills);
            Job savedJob = jobRepository.save(newJob);
            CreateJobDto createJobDtoResponse = modelMapper.map(savedJob, CreateJobDto.class);
            return ApiResponse.success(
                    "Job created successfully",
                    HttpStatus.CREATED,
                    createJobDtoResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<JobResponse> getAllJobs(int pageNo, int pageSize,
                                               String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.findAll(pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobDto> jobDtoList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setPageNo(jobs.getNumber());
        metaData.setPageSize(jobs.getSize());
        metaData.setTotalElements(jobs.getTotalElements());
        metaData.setTotalPages(jobs.getTotalPages());
        metaData.setLastPage(jobs.isLast());
        JobResponse jobResponse = new JobResponse();
        jobResponse.setMeta(metaData);
        jobResponse.setData(jobDtoList);
        return ApiResponse.success(
                "Jobs fetched successfully",
                HttpStatus.OK,
                jobResponse
        );
    }

    @Override
    public ApiResponse<JobDto> getJobById(String id) {
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        JobDto JobResponse = modelMapper.map(findJob, JobDto.class);
        return ApiResponse.success(
                "Job fetched successfully",
                HttpStatus.OK,
                JobResponse
        );
    }

    @Override
    public ApiResponse<UpdateJobDto> updateJobById(String id, UpdateJobDto updateJobDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        if (updatedByUser.getCompany() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
        }
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        Company findCompany = companyRepository.findByName(updateJobDto.getCompany()
                .getName());
        if (findCompany == null) {
            throw new ResourceNotFoundException("Company", "name", updateJobDto.getCompany()
                    .getName());
        }
        findJob.setName(updateJobDto.getName());
        findJob.setDescription(updateJobDto.getDescription());
        findJob.setLocation(updateJobDto.getLocation());
        findJob.setQuantity(updateJobDto.getQuantity());
        findJob.setSalary(updateJobDto.getSalary());
        findJob.setLevel(updateJobDto.getLevel());
        findJob.setUpdatedBy(updatedByUser);
        findJob.setUpdatedAt(LocalDateTime.now());
        findJob.setCompany(findCompany);
        findJob.setStartDate(updateJobDto.getStartDate());
        findJob.setEndDate(updateJobDto.getEndDate());
        findJob.setActive(updateJobDto.isActive());
        List<Skill> skills = getSkills(updateJobDto.getSkills());
        findJob.setSkills(skills);
        Job savedJob = jobRepository.save(findJob);
        UpdateJobDto updateJobDtoResponse = modelMapper.map(savedJob, UpdateJobDto.class);
        return ApiResponse.success(
                "Job updated successfully",
                HttpStatus.OK,
                updateJobDtoResponse
        );
    }

    @Override
    public ApiResponse<DeleteJobDto> deleteJobById(String id) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        if (deletedByUser.getCompany() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
        }
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        findJob.setDeleted(true);
        findJob.setActive(false);
        findJob.setDeletedBy(deletedByUser);
        findJob.setDeletedAt(LocalDateTime.now());
        Job saveDelete = jobRepository.save(findJob);
        DeleteJobDto deleteJobDtoResponse = modelMapper.map(saveDelete, DeleteJobDto.class);
        return ApiResponse.success(
                "Job deleted successfully",
                HttpStatus.OK,
                deleteJobDtoResponse
        );
    }

    private List<Skill> getSkills(List<SkillDto> skills) {
        List<Skill> skillList = new ArrayList<>();
        for (SkillDto skillDto : skills) {
            Skill existingSkill = skillRepository.findByName(skillDto.getName());
            if (existingSkill == null) {
                Skill newSkill = new Skill();
                newSkill.setName(skillDto.getName());
                skillRepository.save(newSkill);
                skillList.add(newSkill);
            }
            if (existingSkill != null) {
                skillList.add(existingSkill);
            }
        }
        return skillList;
    }


    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }
}
