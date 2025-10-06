package com.newton.kurazetuapi.core.voter.models;

import com.newton.kurazetuapi.core.county.models.County;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "voter_registrations", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_county", columnList = "county_id"),
        @Index(name = "idx_registration_date", columnList = "registrationDate"),
        @Index(name = "idx_confirmed", columnList = "confirmed"),
        @Index(name = "idx_ip_address", columnList = "ipAddress")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoterRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 200)
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than 120")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotNull(message = "County is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @Column(length = 45)
    private String ipAddress;

    @NotNull(message = "Registration date is required")
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private Boolean confirmed = false;

    @Column
    private LocalDateTime confirmedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}