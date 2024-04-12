package com.frankie.workdev.repository;

import com.frankie.workdev.dto.company.CompanyInfo;
import com.frankie.workdev.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByName(String name);


    Boolean existsByName(String name);

    @Query("SELECT c FROM Company c WHERE " +
            "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:address IS NULL OR c.address LIKE CONCAT('%', :address, '%'))")
    Page<Company> searchCompany(String name, String address, Pageable pageable);
}
