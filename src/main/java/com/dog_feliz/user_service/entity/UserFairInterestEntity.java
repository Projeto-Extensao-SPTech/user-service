package com.dog_feliz.user_service.entity;
import jakarta.persistence.*;
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