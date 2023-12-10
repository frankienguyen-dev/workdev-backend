package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Permission findByName(String name);
}
