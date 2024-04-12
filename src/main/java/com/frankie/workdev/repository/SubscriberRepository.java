package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    Subscriber findByEmail(String email);
}
