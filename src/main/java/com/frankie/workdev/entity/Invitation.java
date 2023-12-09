package com.frankie.workdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invitations")
public class Invitation {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiverUser;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private String content;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    private void setRandomId() {
        this.id = generateId();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
