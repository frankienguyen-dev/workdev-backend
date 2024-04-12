package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Resume;
import com.frankie.workdev.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResumeRepository extends JpaRepository<Resume, String> {

    @Query("SELECT r FROM Resume r WHERE r.createdBy = :createdBy")
    Page<Resume> findByCreatedBy(User createdBy, Pageable pageable);
}
