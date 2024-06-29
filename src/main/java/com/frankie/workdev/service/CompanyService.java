package com.frankie.workdev.service;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.company.*;

public interface CompanyService {
    ApiResponse<CreateCompanyDto> createNewCompany(CreateCompanyDto createCompanyDto);

    ApiResponse<CompanyListResponse> getAllCompanies(int pageNo, int pageSize,
                                                     String sortBy, String sortDir);

    ApiResponse<CompanyResponse> getCompanyById(String id);

    ApiResponse<UpdateCompanyDto> updateCompanyById(String id,
                                                    UpdateCompanyDto updateCompanyDto);

    ApiResponse<DeleteCompanyDto> deletedCompanyById(String id);

    ApiResponse<CompanyListResponse> searchCompany(String name, String address, int pageNo, int pageSize,
                                                   String sortBy, String sortDir);

    ApiResponse<CompanyResponse> getMyCompanyInfo();

}
