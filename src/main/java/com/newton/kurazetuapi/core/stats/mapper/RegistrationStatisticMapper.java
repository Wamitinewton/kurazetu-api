package com.newton.kurazetuapi.core.stats.mapper;

import com.newton.kurazetuapi.core.county.mapper.CountyMapper;
import com.newton.kurazetuapi.core.stats.dto.RegistrationStatisticDto;
import com.newton.kurazetuapi.core.stats.models.RegistrationStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationStatisticMapper {

    private final CountyMapper countyMapper;

    public RegistrationStatisticDto toDto(RegistrationStatistic statistic) {
        if (statistic == null) {
            return null;
        }

        return RegistrationStatisticDto.builder()
                .id(statistic.getId())
                .county(countyMapper.toDto(statistic.getCounty()))
                .statisticDate(statistic.getStatisticDate())
                .totalRegistrations(statistic.getTotalRegistrations())
                .confirmedRegistrations(statistic.getConfirmedRegistrations())
                .pendingRegistrations(statistic.getPendingRegistrations())
                .averageAge(statistic.getAverageAge())
                .minAge(statistic.getMinAge())
                .maxAge(statistic.getMaxAge())
                .registrationsToday(statistic.getRegistrationsToday())
                .registrationsThisWeek(statistic.getRegistrationsThisWeek())
                .registrationsThisMonth(statistic.getRegistrationsThisMonth())
                .createdAt(statistic.getCreatedAt())
                .build();
    }
}
