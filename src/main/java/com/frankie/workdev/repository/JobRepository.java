package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
}
