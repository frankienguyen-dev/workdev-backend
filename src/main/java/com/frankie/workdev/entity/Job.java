package com.frankie.workdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    private String id;
    private String name;
    private String description;
    private String responsibility;
    private String location;
    private int quantity;
    private Long salary;
    private String level;
    private String education;
    private String jobType;
    private String experience;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    private User deletedBy;
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private boolean isActive = true;
    private boolean isDeleted = false;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "jobs_skills",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
    )
    private List<Skill> skills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @PrePersist
    private void setRandomId() {
        this.id = generateId();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
