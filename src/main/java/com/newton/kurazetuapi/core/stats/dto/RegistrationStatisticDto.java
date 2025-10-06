package com.newton.kurazetuapi.core.stats.dto;
import com.newton.kurazetuapi.core.county.dto.CountyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationStatisticDto {
    private Long id;
    private CountyDto county;
    private LocalDateTime statisticDate;
    private Long totalRegistrations;
    private Long confirmedRegistrations;
    private Long pendingRegistrations;
    private Double averageAge;
    private Integer minAge;
    private Integer maxAge;
    private Long registrationsToday;
    private Long registrationsThisWeek;
    private Long registrationsThisMonth;
    private LocalDateTime createdAt;
}
