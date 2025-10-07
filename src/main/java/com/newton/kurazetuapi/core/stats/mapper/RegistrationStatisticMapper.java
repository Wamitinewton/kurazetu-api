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
                .totalVerifiedRegistrations(statistic.getTotalVerifiedRegistrations())
                .statisticDate(statistic.getStatisticDate())
                .build();
    }
}