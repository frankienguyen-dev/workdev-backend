package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Subscriber;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    Subscriber findByEmail(String email);

    @Query("SELECT s FROM Subscriber s WHERE (s.email IS NULL OR s.email LIKE CONCAT('%', :email, '%')) ")
    Page<Subscriber> searchSubscriberByEmail(String email, Pageable pageable);

    @Query("SELECT s FROM Subscriber s WHERE s.email = :email")
    List<Subscriber> findAllByEmail(String email);
}
