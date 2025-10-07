package com.newton.kurazetuapi.core.stats.service;

import com.newton.kurazetuapi.core.stats.dto.AllCountiesStatsDto;
import com.newton.kurazetuapi.core.stats.dto.CountyStatsDto;
import com.newton.kurazetuapi.core.stats.dto.OverallStatsDto;

public interface StatsService {

    OverallStatsDto getOverallStats();

    CountyStatsDto getCountyStats(Long countyId);

    AllCountiesStatsDto getAllCountiesStats();
}