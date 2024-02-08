package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.company.*;

public interface CompanyService {
    ApiResponse<CreateCompanyDto> createNewCompany(CreateCompanyDto createCompanyDto);

    ApiResponse<CompanyResponse> getAllCompanies(int pageNo, int pageSize,
                                                 String sortBy, String sortDir);

    ApiResponse<CompanyDto> getCompanyById(String id);

    ApiResponse<UpdateCompanyDto> updateCompanyById(String id,
                                                    UpdateCompanyDto updateCompanyDto);

    ApiResponse<DeleteCompanyDto> deletedCompanyById(String id);

    ApiResponse<CompanyResponse> searchCompany(String name, String address, int pageNo, int pageSize,
                                               String sortBy, String sortDir);
}
