package com.frankie.workdev.repository;

import com.frankie.workdev.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, String> {
    FileEntity findByFileName(String fileName);

    @Query("SELECT f FROM FileEntity f " +
            "LEFT JOIN User u ON u.avatar.id = f.id " +
            "LEFT JOIN Company c1 ON c1.logo.id = f.id " +
            "LEFT JOIN Company c2 ON c2.banner.id = f.id " +
            "LEFT JOIN Resume r ON r.resume.id = f.id " +
            " WHERE u.id IS NULL AND c1.id IS NULL AND c2.id IS NULL AND r.id IS NULL")
    List<FileEntity> findOrphanFiles();

    @Query("SELECT f FROM FileEntity f " +
            "LEFT JOIN User u ON u.avatar.id = f.id " +
            "LEFT JOIN Company c1 ON c1.logo.id = f.id " +
            "LEFT JOIN Company c2 ON c2.banner.id = f.id " +
            "LEFT JOIN Resume r ON r.resume.id = f.id " +
            " WHERE u.id IS NULL AND c1.id IS NULL AND c2.id IS NULL AND r.id IS NULL")
    Page<FileEntity> getOrphanFiles(Pageable pageable);
}
