package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT c FROM Category c WHERE (:name IS NULL OR c.name LIKE CONCAT('%', :name, '%'))")
    Page<Category> searchCategoryByName(String name, Pageable pageable);
}
