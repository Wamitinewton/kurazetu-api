package com.newton.kurazetuapi.core.stats.models;

import com.newton.kurazetuapi.core.county.models.County;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration_statistics", indexes = {
        @Index(name = "idx_stat_date", columnList = "statisticDate"),
        @Index(name = "idx_stat_county", columnList = "county_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private County county;

    @NotNull(message = "Statistic date is required")
    @Column(nullable = false)
    private LocalDateTime statisticDate;

    @Column(nullable = false)
    private Long totalVerifiedRegistrations = 0L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}