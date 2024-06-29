package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.company.*;
import com.frankie.workdev.service.CompanyService;
import com.frankie.workdev.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/companies")
public class CompanyController {
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCompanyDto>> createNewCompany(
            @RequestBody @Valid CreateCompanyDto createCompanyDto) {
        ApiResponse<CreateCompanyDto> createCompany = companyService
                .createNewCompany(createCompanyDto);
        return new ResponseEntity<>(createCompany, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CompanyListResponse>> getAllCompanies(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<CompanyListResponse> getAllCompanies = companyService.getAllCompanies(
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllCompanies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyById(
            @PathVariable("id") String id) {
        ApiResponse<CompanyResponse> getCompanyById = companyService.getCompanyById(id);
        return new ResponseEntity<>(getCompanyById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateCompanyDto>> updateCompanyById(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdateCompanyDto updateCompanyDto) {
        ApiResponse<UpdateCompanyDto> updateCompany = companyService
                .updateCompanyById(id, updateCompanyDto);
        return new ResponseEntity<>(updateCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeleteCompanyDto>> deleteCompanyById(
            @PathVariable("id") String id) {
        ApiResponse<DeleteCompanyDto> deleteCompanyById = companyService
                .deletedCompanyById(id);
        return new ResponseEntity<>(deleteCompanyById, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<CompanyListResponse>> searchCompany(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<CompanyListResponse> searchCompany = companyService.searchCompany(name, address,
                pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(searchCompany, HttpStatus.OK);
    }


    @GetMapping("/my-company")
    public ResponseEntity<ApiResponse<CompanyResponse>> getMyCompany() {
        ApiResponse<CompanyResponse> getMyCompany = companyService.getMyCompanyInfo();
        return new ResponseEntity<>(getMyCompany, HttpStatus.OK);
    }

}
