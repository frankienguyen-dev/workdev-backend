package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    boolean existsByCompany(Company company);
}
