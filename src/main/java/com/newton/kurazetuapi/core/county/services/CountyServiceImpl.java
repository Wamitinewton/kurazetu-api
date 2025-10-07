package com.newton.kurazetuapi.core.county.services;

import com.newton.kurazetuapi.core.county.dto.CountyDto;
import com.newton.kurazetuapi.core.county.mapper.CountyMapper;
import com.newton.kurazetuapi.core.county.repositories.CountyRepository;
import com.newton.kurazetuapi.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final CountyMapper countyMapper;

    @Override
    public List<CountyDto> getAllCounties() {
        return countyRepository.findAll()
                .stream()
                .map(countyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountyDto getCountyById(Long id) {
        return countyRepository.findById(id)
                .map(countyMapper::toDto)
                .orElseThrow(() -> new CustomException("County not found with id: " + id));
    }

    @Override
    public CountyDto getCountyByCode(String code) {
        return countyRepository.findByCode(code)
                .map(countyMapper::toDto)
                .orElseThrow(() -> new CustomException("County not found with code: " + code));
    }
}
