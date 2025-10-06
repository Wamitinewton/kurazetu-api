package com.newton.kurazetuapi.core.stats.repository;

import com.newton.kurazetuapi.core.stats.models.RegistrationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationStatisticRepository extends JpaRepository<RegistrationStatistic, Long> {

    Optional<RegistrationStatistic> findTopByCountyIdOrderByStatisticDateDesc(Long countyId);

    Optional<RegistrationStatistic> findTopByCountyIsNullOrderByStatisticDateDesc();

    List<RegistrationStatistic> findByCountyIdOrderByStatisticDateDesc(Long countyId);

    List<RegistrationStatistic> findByCountyIsNullOrderByStatisticDateDesc();

    @Query("SELECT r FROM RegistrationStatistic r WHERE r.statisticDate >= :startDate ORDER BY r.statisticDate DESC")
    List<RegistrationStatistic> findStatisticsSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT r FROM RegistrationStatistic r WHERE r.county.id = :countyId AND r.statisticDate >= :startDate ORDER BY r.statisticDate DESC")
    List<RegistrationStatistic> findCountyStatisticsSince(@Param("countyId") Long countyId, @Param("startDate") LocalDateTime startDate);
}
