package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Permission findByName(String name);

    @Query("SELECT p FROM Permission p WHERE (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%'))")
    Page<Permission> searchPermission(String name, Pageable pageable);
}
