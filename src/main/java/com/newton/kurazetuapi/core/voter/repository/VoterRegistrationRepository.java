package com.newton.kurazetuapi.core.voter.repository;

import com.newton.kurazetuapi.core.voter.models.VoterRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoterRegistrationRepository extends JpaRepository<VoterRegistration, Long> {

    Optional<VoterRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    List<VoterRegistration> findByCountyId(Long countyId);

    List<VoterRegistration> findByConfirmed(Boolean confirmed);

    List<VoterRegistration> findByCountyIdAndConfirmed(Long countyId, Boolean confirmed);

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.confirmed = true")
    Long countConfirmedRegistrations();

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.confirmed = false")
    Long countPendingRegistrations();

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.county.id = :countyId")
    Long countByCountyId(@Param("countyId") Long countyId);

    @Query("SELECT COUNT(v) FROM VoterRegistration v WHERE v.registrationDate >= :startDate")
    Long countRegistrationsSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT AVG(v.age) FROM VoterRegistration v WHERE v.confirmed = true")
    Double getAverageAge();

    @Query("SELECT AVG(v.age) FROM VoterRegistration v WHERE v.county.id = :countyId AND v.confirmed = true")
    Double getAverageAgeByCounty(@Param("countyId") Long countyId);
}
