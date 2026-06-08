package com.dog.feliz.user.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "user_fair_interest",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_user_fair",
                columnNames = {"user_id", "fair_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class UserFairInterestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "fair_id", nullable = false)
    private Long fairId;

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    public UserFairInterestEntity(Long userId, Long fairId) {
        this.userId = userId;
        this.fairId = fairId;
        this.createdAt = ZonedDateTime.now();
    }
}