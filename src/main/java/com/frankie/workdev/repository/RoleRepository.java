package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);

    @Query("SELECT r FROM Role r WHERE (:name IS NULL OR r.name LIKE CONCAT('%', :name, '%'))")
    Page<Role> searchRole(String name, Pageable pageable);
}
