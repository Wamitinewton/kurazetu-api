package com.newton.kurazetuapi.core.voter.repository;

import com.newton.kurazetuapi.core.voter.models.VoterRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRegistrationRepository extends JpaRepository<VoterRegistration, Long> {

    Optional<VoterRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIpAddress(String ipAddress);

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.confirmed = true")
    Long countConfirmedRegistrations();

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.county.id = :countyId AND v.confirmed = true")
    Long countByCountyId(@Param("countyId") Long countyId);
}