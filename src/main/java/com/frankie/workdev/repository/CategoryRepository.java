package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
