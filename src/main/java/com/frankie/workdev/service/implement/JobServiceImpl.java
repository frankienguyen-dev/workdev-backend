package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.job.*;
import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.*;
import com.frankie.workdev.exception.ApiException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.*;
import com.frankie.workdev.service.JobService;
import com.frankie.workdev.util.UserInfoUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private SkillRepository skillRepository;
    private JobRepository jobRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreateJobResponse> createJob(CreateJobDto createJobDto) {
        try {
            JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
            User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            if (createdByUser.getCompany() == null && !createdByUser.getRoles().get(0).getName()
                    .equals("ROLE_ADMIN")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
            }
            Company findCompany = companyRepository.findByName(createJobDto
                    .getCompany().getName());
            if (findCompany == null) {
                throw new ResourceNotFoundException("Company", "name", createJobDto.getCompany()
                        .getName());
            }
            Category findCategory = categoryRepository.findById(createJobDto.getCategory().getId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Category", "id", createJobDto.getCategory().getId())
                    );
            Job newJob = new Job();
            newJob.setName(createJobDto.getName());
            newJob.setDescription(createJobDto.getDescription());
            newJob.setResponsibility(createJobDto.getResponsibility());
            newJob.setLocation(createJobDto.getLocation());
            newJob.setQuantity(createJobDto.getQuantity());
            newJob.setSalary(createJobDto.getSalary());
            newJob.setLevel(createJobDto.getLevel());
            newJob.setEducation(createJobDto.getEducation());
            newJob.setJobType(createJobDto.getJobType());
            newJob.setExperience(createJobDto.getExperience());
            newJob.setCategory(findCategory);
            newJob.setCreatedBy(createdByUser);
            newJob.setCreatedAt(LocalDateTime.now());
            newJob.setCompany(findCompany);
            newJob.setStartDate(createJobDto.getStartDate());
            newJob.setEndDate(createJobDto.getEndDate());
            newJob.setIsActive(true);
            newJob.setUser(createdByUser);
            List<Skill> skills = getSkills(createJobDto.getSkills());
            newJob.setSkills(skills);
            createdByUser.getJobs().add(newJob);
            Job savedJob = jobRepository.save(newJob);
//            User userTest = userRepository.save(createdByUser);
            CreateJobResponse createJobResponse = modelMapper.map(savedJob, CreateJobResponse.class);
            return ApiResponse.success(
                    "Job created successfully",
                    HttpStatus.CREATED,
                    createJobResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<JobListResponse> getAllJobs(int pageNo, int pageSize,
                                                   String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.findAll(pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobResponse> jobDtoList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobResponse.class);
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
        JobListResponse jobResponse = new JobListResponse();
        jobResponse.setMeta(metaData);
        jobResponse.setData(jobDtoList);
        return ApiResponse.success(
                "Jobs fetched successfully",
                HttpStatus.OK,
                jobResponse
        );
    }

    @Override
    public ApiResponse<JobResponse> getJobById(String id) {
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        JobResponse JobResponse = modelMapper.map(findJob, com.frankie.workdev.dto.job.JobResponse.class);

        return ApiResponse.success(
                "Job fetched successfully",
                HttpStatus.OK,
                JobResponse
        );
    }

    @Override
    public ApiResponse<UpdateJobResponse> updateJobById(String id, UpdateJobDto updateJobDto) {
        try {
            JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
            User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            if (updatedByUser.getCompany() == null && !updatedByUser.getRoles().get(0)
                    .getName().equals("ROLE_ADMIN")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
            }
            Job findJob = jobRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Job", "id", id)
            );
            Category findCategory = categoryRepository.findById(updateJobDto.getCategory().getId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Category",
                                    "id", updateJobDto.getCategory().getId())
                    );
            Company findCompany = companyRepository.findByName(updateJobDto.getCompany()
                    .getName());
            if (findCompany == null) {
                throw new ResourceNotFoundException("Company", "name", updateJobDto.getCompany()
                        .getName());
            }
            if (!updatedByUser.getId().equalsIgnoreCase(findJob.getCreatedBy().getId()) &&
                    !updatedByUser.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "User is not authorized to update this job");
            }
            findJob.setName(updateJobDto.getName());
            findJob.setDescription(updateJobDto.getDescription());
            findJob.setResponsibility(updateJobDto.getResponsibility());
            findJob.setLocation(updateJobDto.getLocation());
            findJob.setQuantity(updateJobDto.getQuantity());
            findJob.setSalary(updateJobDto.getSalary());
            findJob.setLevel(updateJobDto.getLevel());
            findJob.setEducation(updateJobDto.getEducation());
            findJob.setJobType(updateJobDto.getJobType());
            findJob.setExperience(updateJobDto.getExperience());
            findJob.setCategory(findCategory);
            findJob.setUpdatedBy(updatedByUser);
            findJob.setUpdatedAt(LocalDateTime.now());
            findJob.setCompany(findCompany);
            findJob.setStartDate(updateJobDto.getStartDate());
            findJob.setEndDate(updateJobDto.getEndDate());
            findJob.setIsActive(updateJobDto.getIsActive());
            List<Skill> skills = getSkills(updateJobDto.getSkills());
            findJob.setSkills(skills);
            Job savedJob = jobRepository.save(findJob);
            UpdateJobResponse updateJobResponse = modelMapper.map(savedJob, UpdateJobResponse.class);
            return ApiResponse.success(
                    "Job updated successfully",
                    HttpStatus.OK,
                    updateJobResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<DeleteJobDto> deleteJobById(String id) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        if (deletedByUser.getCompany() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User is not a company");
        }
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        findJob.setIsActive(false);
        findJob.setIsDeleted(true);
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

    @Override
    public ApiResponse<JobListResponse> searchJob(String name, String location, Long salary,
                                                  int pageNo, int pageSize,
                                                  String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;

        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.searchJob(name, location, salary, pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobResponse> jobList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobResponse.class);
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
        JobListResponse jobResponse = new JobListResponse();
        jobResponse.setData(jobList);
        jobResponse.setMeta(metaData);
        return ApiResponse.success(
                "Search Job fetched successfully",
                HttpStatus.OK,
                jobResponse
        );

    }

    @Override
    public ApiResponse<JobResponse> favoriteJob(String id) {
        JwtUserInfo getInfoUser = userInfoUtils.getJwtUserInfo();
        Job findJob = jobRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Job", "id", id)
        );
        User findUser = userRepository.findById(getInfoUser.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", getInfoUser.getId())
        );
        if (findUser.getFavoriteJob().contains(findJob)) {
            findUser.getFavoriteJob().remove(findJob);
            findJob.getUserFavoriteJob().remove(findUser);
            userRepository.save(findUser);
            jobRepository.save(findJob);
            JobResponse response = modelMapper.map(findJob, JobResponse.class);
            return ApiResponse.success(
                    "Job removed from favorite successfully",
                    HttpStatus.OK,
                    response
            );
        } else {
            findUser.getFavoriteJob().add(findJob);
            userRepository.save(findUser);
            findJob.getUserFavoriteJob().add(findUser);
            Job saveJob = jobRepository.save(findJob);
            JobResponse response = modelMapper.map(saveJob, JobResponse.class);
            return ApiResponse.success(
                    "Job added to favorite successfully",
                    HttpStatus.OK,
                    response
            );
        }

    }

    @Override
    public ApiResponse<JobListResponse> getFavoriteJobs(int pageNo, int pageSize,
                                                        String sortBy, String sortDir) {
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User user = userRepository.findByEmail(getUserInfo.getEmail());
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.findAllFavoriteJobsByUserId(user.getId(), pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobResponse> jobList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobResponse.class);
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
        JobListResponse jobResponse = new JobListResponse();
        jobResponse.setData(jobList);
        jobResponse.setMeta(metaData);
        return ApiResponse.success(
                "Favorite Job fetched successfully",
                HttpStatus.OK,
                jobResponse
        );
    }

    @Override
    public ApiResponse<JobListResponse> getJobListByUser(int pageNo, int pageSize,
                                                         String sortBy, String sortDir) {
        JwtUserInfo getUserInfo = userInfoUtils.getJwtUserInfo();
        User user = userRepository.findByEmail(getUserInfo.getEmail());
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.findListJobByUserId(user.getId(), pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobResponse> jobList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobResponse.class);
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
        JobListResponse jobResponse = new JobListResponse();
        jobResponse.setData(jobList);
        jobResponse.setMeta(metaData);
        return ApiResponse.success(
                "List Job fetched successfully",
                HttpStatus.OK,
                jobResponse
        );
    }

    @Override
    public ApiResponse<JobListResponse> getAllJobByCompanyId(String companyId, int pageNo,
                                                             int pageSize, String sortBy,
                                                             String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.getAllJobByCompanyId(companyId, pageable);
        List<Job> jobContentList = jobs.getContent();
        List<JobResponse> jobList = jobContentList.stream()
                .map(job -> {
                    try {
                        return modelMapper.map(job, JobResponse.class);
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
        JobListResponse jobResponse = new JobListResponse();
        jobResponse.setData(jobList);
        jobResponse.setMeta(metaData);
        return ApiResponse.success(
                "List job by company id fetched successfully",
                HttpStatus.OK,
                jobResponse
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
}
