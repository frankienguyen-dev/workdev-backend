package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByName(String name);

    Boolean existsByName(String name);
}
