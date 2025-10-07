package com.newton.kurazetuapi.core.stats.controller;

import com.newton.kurazetuapi.core.stats.dto.AllCountiesStatsDto;
import com.newton.kurazetuapi.core.stats.dto.CountyStatsDto;
import com.newton.kurazetuapi.core.stats.dto.OverallStatsDto;
import com.newton.kurazetuapi.core.stats.service.StatsService;
import com.newton.kurazetuapi.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/overall")
    public ResponseEntity<ApiResponse> getOverallStats() {
        OverallStatsDto stats = statsService.getOverallStats();
        return ResponseEntity.ok(new ApiResponse("Overall statistics retrieved successfully", stats));
    }

    @GetMapping("/county/{countyId}")
    public ResponseEntity<ApiResponse> getCountyStats(@PathVariable Long countyId) {
        CountyStatsDto stats = statsService.getCountyStats(countyId);
        return ResponseEntity.ok(new ApiResponse("County statistics retrieved successfully", stats));
    }

    @GetMapping("/counties")
    public ResponseEntity<ApiResponse> getAllCountiesStats() {
        AllCountiesStatsDto stats = statsService.getAllCountiesStats();
        return ResponseEntity.ok(new ApiResponse("All counties statistics retrieved successfully", stats));
    }
}