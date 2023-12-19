package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, String> {
    Skill findByName(String name);
}
