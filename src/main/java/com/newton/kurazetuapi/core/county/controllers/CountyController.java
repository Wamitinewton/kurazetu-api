package com.newton.kurazetuapi.core.county.controllers;

import com.newton.kurazetuapi.core.county.dto.CountyDto;
import com.newton.kurazetuapi.core.county.services.CountyService;
import com.newton.kurazetuapi.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/counties")
@RequiredArgsConstructor
public class CountyController {

    private final CountyService countyService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCounties() {
        List<CountyDto> counties = countyService.getAllCounties();
        return ResponseEntity.ok(new ApiResponse("Counties retrieved successfully", counties));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCountyById(@PathVariable Long id) {
        CountyDto county = countyService.getCountyById(id);
        return ResponseEntity.ok(new ApiResponse("County retrieved successfully", county));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse> getCountyByCode(@PathVariable String code) {
        CountyDto county = countyService.getCountyByCode(code);
        return ResponseEntity.ok(new ApiResponse("County retrieved successfully", county));
    }
}
