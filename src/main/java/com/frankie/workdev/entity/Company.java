package com.frankie.workdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    private String id;
    private String name;
    private String companyType;
    private String description;
    private String companyVision;
    private String companyBenefit;
    private String address;
    private String email;
    private String phoneNumber;
    private String website;
    private Long teamSize;
    private LocalDateTime foundedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logo_id", referencedColumnName = "id")
    private FileEntity logo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private FileEntity banner;

    @OneToMany(mappedBy = "company")
    private List<User> users;

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
    private boolean isDeleted = false;


    @PrePersist
    private void setRandomId() {
        this.id = generateId();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
