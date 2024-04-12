package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    boolean existsByCompany(Company company);

    @Query("SELECT u FROM User u WHERE " +
            "(:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) ")
    Page<User> searchUser(String email, Pageable pageable);
}
