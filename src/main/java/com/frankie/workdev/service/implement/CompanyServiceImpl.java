package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.*;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.FileEntity;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.FileRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private ModelMapper modelMapper;
    private FileRepository fileRepository;
    private UserInfoUtils userInfoUtils;

    @Override
    public ApiResponse<CreateCompanyDto> createNewCompany(CreateCompanyDto createCompanyDto) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        if (createdByUser.getCompany() != null && !createdByUser.getRoles().get(0)
                .getName().equalsIgnoreCase("ROLE_ADMIN")) {
            throw new ResourceExistingException(createdByUser.getCompany().getName(), "id",
                    createdByUser.getCompany().getId());
        }
        Boolean existingCompany = companyRepository.existsByName(createCompanyDto.getName());
        if (existingCompany) {
            throw new ResourceExistingException("Company", "name",
                    createCompanyDto.getName());
        }
        Company newCompany = new Company();
        newCompany.setId(createCompanyDto.getId());
        newCompany.setName(createCompanyDto.getName());
        newCompany.setCompanyType(createCompanyDto.getCompanyType());
        newCompany.setDescription(createCompanyDto.getDescription());
        newCompany.setCompanyBenefit(createCompanyDto.getCompanyBenefit());
        newCompany.setAddress(createCompanyDto.getAddress());
        newCompany.setEmail(createCompanyDto.getEmail());
        newCompany.setPhoneNumber(createCompanyDto.getPhoneNumber());
        newCompany.setWebsite(createCompanyDto.getWebsite());
        newCompany.setTeamSize(createCompanyDto.getTeamSize());
        newCompany.setFoundedDate(createCompanyDto.getFoundedDate());
        if (createCompanyDto.getLogo() != null
                && createCompanyDto.getLogo().getId() != null) {
            FileEntity logo = fileRepository.findById(createCompanyDto.getLogo().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("File", "id",
                            createCompanyDto.getLogo().getId()));
            newCompany.setLogo(logo);

        } else {
            newCompany.setLogo(null);

        }
        if (createCompanyDto.getBanner() != null
                && createCompanyDto.getBanner().getId() != null) {
            FileEntity banner = fileRepository.findById(createCompanyDto.getBanner().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("File", "id",
                            createCompanyDto.getBanner().getId()));
            newCompany.setBanner(banner);
        } else {
            newCompany.setBanner(null);
        }
        newCompany.setCreatedAt(LocalDateTime.now());
        newCompany.setCreatedBy(createdByUser);
        if (!createdByUser.getRoles().get(0)
                .getName().equalsIgnoreCase("ROLE_ADMIN")) {
            createdByUser.setCompany(newCompany);
        }
        Company savedCompany = companyRepository.save(newCompany);
        userRepository.save(createdByUser);
        CreateCompanyDto createCompany = modelMapper.map(savedCompany, CreateCompanyDto.class);
        createCompany.setCreatedBy(getUserInfoFromToken);
        createCompany.setCreatedAt(savedCompany.getCreatedAt());
        return ApiResponse.success(
                "Company created successfully",
                HttpStatus.CREATED,
                createCompany
        );
    }

    @Override
    public ApiResponse<CompanyResponse> getAllCompanies(int pageNo, int pageSize,
                                                        String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;

        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Company> companies = companyRepository.findAll(pageable);
        List<Company> companyContentList = companies.getContent();
        List<CompanyDto> companyDtoList = companyContentList.stream()
                .map(company -> {
                    try {
                        return modelMapper.map(company, CompanyDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(companies.isLast());
        metaData.setTotalElements(companies.getTotalElements());
        metaData.setTotalPages(companies.getTotalPages());
        metaData.setPageSize(companies.getSize());
        metaData.setPageNo(companies.getNumber());
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setData(companyDtoList);
        companyResponse.setMeta(metaData);
        return ApiResponse.success(
                "Companies fetched successfully",
                HttpStatus.OK,
                companyResponse
        );
    }


    @Override
    public ApiResponse<CompanyDto> getCompanyById(String id) {
        try {
            Company findCompany = companyRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Company", "id", id)
            );
            CompanyDto companyDto = modelMapper.map(findCompany, CompanyDto.class);
            return ApiResponse.success(
                    "Company fetched successfully",
                    HttpStatus.OK,
                    companyDto
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<UpdateCompanyDto> updateCompanyById(
            String id, UpdateCompanyDto updateCompanyDto) {
        try {
            JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
            User updatedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
            Company findCompany = companyRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Company", "id", id)
            );
            if (updateCompanyDto.getName() != null
                    && !updateCompanyDto.getName().equalsIgnoreCase(findCompany.getName())) {
                Company existingCompany = companyRepository.findByName(updateCompanyDto.getName());
                if (existingCompany != null) {
                    throw new ResourceExistingException("Company", "name",
                            updateCompanyDto.getName());
                }
            }

            if (updateCompanyDto.getLogo() != null
                    && updateCompanyDto.getLogo().getId() != null) {
                FileEntity logo = fileRepository.findById(updateCompanyDto.getLogo().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("File", "id",
                                updateCompanyDto.getLogo().getId()));
                findCompany.setLogo(logo);

            } else {
                findCompany.setLogo(null);

            }
            if( updateCompanyDto.getBanner() != null
                    && updateCompanyDto.getBanner().getId() != null) {
                FileEntity banner = fileRepository.findById(updateCompanyDto.getBanner().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("File", "id",
                                updateCompanyDto.getBanner().getId()));
                findCompany.setBanner(banner);
            } else {
                findCompany.setBanner(null);
            }

            findCompany.setName(updateCompanyDto.getName());
            findCompany.setDescription(updateCompanyDto.getDescription());
            findCompany.setCompanyBenefit(updateCompanyDto.getCompanyBenefit());
            findCompany.setCompanyType(updateCompanyDto.getCompanyType());
            findCompany.setAddress(updateCompanyDto.getAddress());
            findCompany.setEmail(updateCompanyDto.getEmail());
            findCompany.setPhoneNumber(updateCompanyDto.getPhoneNumber());
            findCompany.setWebsite(updateCompanyDto.getWebsite());
            findCompany.setTeamSize(updateCompanyDto.getTeamSize());
            findCompany.setFoundedDate(updateCompanyDto.getFoundedDate());
            findCompany.setUpdatedBy(updatedByUser);
            findCompany.setUpdatedAt(LocalDateTime.now());
            Company saveUpdate = companyRepository.save(findCompany);
            UpdateCompanyDto updateCompany = modelMapper.map(saveUpdate, UpdateCompanyDto.class);
            updateCompany.setUpdatedBy(getUserInfoFromToken);
            updateCompany.setUpdatedAt(saveUpdate.getUpdatedAt());
            return ApiResponse.success(
                    "Company updated successfully",
                    HttpStatus.OK,
                    updateCompany
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<DeleteCompanyDto> deletedCompanyById(String id) {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Company findCompany = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", id)
        );
        findCompany.setIsDeleted(true);
        findCompany.setDeletedAt(LocalDateTime.now());
        findCompany.setDeletedBy(deletedByUser);
        Company saveDelete = companyRepository.save(findCompany);
        DeleteCompanyDto deleteCompany = modelMapper.map(saveDelete, DeleteCompanyDto.class);
        deleteCompany.setDeletedBy(getUserInfoFromToken);
        deleteCompany.setDeletedAt(saveDelete.getDeletedAt());
        deleteCompany.setIsDeleted(saveDelete.getIsDeleted());
        return ApiResponse.success(
                "Company deleted successfully",
                HttpStatus.OK,
                deleteCompany
        );
    }

    @Override
    public ApiResponse<CompanyResponse> searchCompany(String name, String address,
                                                      int pageNo, int pageSize,
                                                      String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        int adjustedPageNo = pageNo > 0 ? pageNo - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPageNo, pageSize, sort);
        Page<Company> companies = companyRepository.searchCompany(name, address, pageable);
        List<Company> companyListContent = companies.getContent();
        List<CompanyDto> companyDtoList = companyListContent.stream()
                .map(company -> {
                    try {
                        return modelMapper.map(company, CompanyDto.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }).collect(Collectors.toList());
        MetaData metaData = new MetaData();
        metaData.setLastPage(companies.isLast());
        metaData.setPageNo(companies.getNumber());
        metaData.setTotalPages(companies.getTotalPages());
        metaData.setTotalElements(companies.getTotalElements());
        metaData.setPageSize(companies.getSize());
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setData(companyDtoList);
        companyResponse.setMeta(metaData);
        return ApiResponse.success(
                "Company search successfully",
                HttpStatus.OK,
                companyResponse
        );

    }

    @Override
    public ApiResponse<CompanyDto> getMyCompanyInfo() {
        JwtUserInfo getUserInfoFromToken = userInfoUtils.getJwtUserInfo();
        User findUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Company findCompany = findUser.getCompany();
        if (findCompany == null) {
            throw new ResourceNotFoundException("Company", "user id", findUser.getId());
        }
        CompanyDto companyDto = modelMapper.map(findCompany, CompanyDto.class);
        return ApiResponse.success(
                "Company fetched successfully",
                HttpStatus.OK,
                companyDto
        );
    }
}
