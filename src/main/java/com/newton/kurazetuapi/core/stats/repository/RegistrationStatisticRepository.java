package com.newton.kurazetuapi.core.stats.repository;

import com.newton.kurazetuapi.core.stats.models.RegistrationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RegistrationStatisticRepository extends JpaRepository<RegistrationStatistic, Long> {

    Optional<RegistrationStatistic> findTopByCountyIdOrderByStatisticDateDesc(Long countyId);

    Optional<RegistrationStatistic> findTopByCountyIsNullOrderByStatisticDateDesc();

    @Query("SELECT r FROM RegistrationStatistic r WHERE r.county.id = :countyId AND r.statisticDate >= :startDate ORDER BY r.statisticDate DESC")
    Optional<RegistrationStatistic> findLatestCountyStatisticSince(@Param("countyId") Long countyId, @Param("startDate") LocalDateTime startDate);
}