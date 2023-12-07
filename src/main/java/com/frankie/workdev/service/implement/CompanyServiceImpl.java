package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.apiResponse.MetaData;
import com.frankie.workdev.dto.company.*;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.CompanyRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private ModelMapper modelMapper;


    @Override
    public ApiResponse<CreateCompanyDto> createNewCompany(CreateCompanyDto createCompanyDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromJwtToken();
        User createdByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Boolean existingCompany = companyRepository.existsByName(createCompanyDto.getName());
        if (existingCompany) {
            throw new ResourceExistingException("Company", "name",
                    createCompanyDto.getName());
        }
        Company newCompany = new Company();
        newCompany.setId(createCompanyDto.getId());
        newCompany.setName(createCompanyDto.getName());
        newCompany.setDescription(createCompanyDto.getDescription());
        newCompany.setAddress(createCompanyDto.getAddress());
        newCompany.setLogo(createCompanyDto.getLogo());
        newCompany.setCreatedAt(LocalDateTime.now());
        newCompany.setCreatedBy(createdByUser);
        Company savedCompany = companyRepository.save(newCompany);
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
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
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
        JwtUserInfo getUserInfoFromToken = getUserInfoFromJwtToken();
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
        findCompany.setName(updateCompanyDto.getName());
        findCompany.setDescription(updateCompanyDto.getDescription());
        findCompany.setAddress(updateCompanyDto.getAddress());
        findCompany.setLogo(updateCompanyDto.getLogo());
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
    }

    @Override
    public ApiResponse<DeleteCompanyDto> deletedCompanyById(String id) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromJwtToken();
        User deletedByUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        Company findCompany = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", id)
        );
        findCompany.setDeleted(true);
        findCompany.setDeletedAt(LocalDateTime.now());
        findCompany.setDeletedBy(deletedByUser);
        Company saveDelete = companyRepository.save(findCompany);
        DeleteCompanyDto deleteCompany = modelMapper.map(saveDelete, DeleteCompanyDto.class);
        deleteCompany.setDeletedBy(getUserInfoFromToken);
        deleteCompany.setDeletedAt(saveDelete.getDeletedAt());
        deleteCompany.setDeleted(saveDelete.isDeleted());
        return ApiResponse.success(
                "Company deleted successfully",
                HttpStatus.OK,
                deleteCompany
        );
    }

    private JwtUserInfo getUserInfoFromJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }
}
