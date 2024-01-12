package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface JobRepository extends JpaRepository<Job, String> {

    @Query("SELECT j FROM Job j WHERE j.name LIKE CONCAT('%', :name, '%')" +
            "OR j.location LIKE CONCAT('%', :location, '%') OR j.salary >= :salary")
    Page<Job> searchJob(String name, String location, Long salary, Pageable pageable);
}
