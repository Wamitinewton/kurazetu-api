package com.newton.kurazetuapi.core.county.services;

import com.newton.kurazetuapi.core.county.dto.CountyDto;

import java.util.List;

public interface CountyService {

    List<CountyDto> getAllCounties();

    CountyDto getCountyById(Long id);

    CountyDto getCountyByCode(String code);
}
