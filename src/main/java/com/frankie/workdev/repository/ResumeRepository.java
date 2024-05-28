package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Resume;
import com.frankie.workdev.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, String> {

    @Query("SELECT r FROM Resume r WHERE r.createdBy = :createdBy")
    Page<Resume> findByCreatedBy(User createdBy, Pageable pageable);


    @Query("SELECT r FROM Resume r WHERE r.user.id = :userId")
    Page<Resume> findResumeByUserId(String userId, Pageable pageable);

    @Query("SELECT r FROM Resume r WHERE (:email IS NULL OR r.user.email LIKE CONCAT('%', :email, '%')) ")
    Page<Resume> searchResumeByEmail(String email, Pageable pageable);
}
