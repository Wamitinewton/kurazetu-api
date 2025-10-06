package com.newton.kurazetuapi.core.email.repository;

import com.newton.kurazetuapi.core.email.models.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {

    Optional<EmailConfirmation> findByToken(String token);

    Optional<EmailConfirmation> findByVoterRegistrationId(Long voterRegistrationId);

    boolean existsByToken(String token);

    @Query("SELECT e FROM EmailConfirmation e WHERE e.expiryDate < :currentTime AND e.used = false")
    List<EmailConfirmation> findExpiredUnusedTokens(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT COUNT(e) FROM EmailConfirmation e WHERE e.used = false AND e.expiryDate > :currentTime")
    Long countValidUnusedTokens(@Param("currentTime") LocalDateTime currentTime);
}
