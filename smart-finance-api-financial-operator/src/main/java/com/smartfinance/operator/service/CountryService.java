package com.smartfinance.operator.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.CountryDTO;
import com.smartfinance.operator.entity.Country;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.operator.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CountryService {
  private final CountryRepository repository;
  private final Mapper.Builder<Country, CountryDTO> countryMapperBuilder;

  public Country get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Country not found"));
  }

  public CountryDTO find(UUID reference) {
    return countryMapperBuilder.dtoMapper().dto(get(reference));
  }

  public List<CountryDTO> list() {
    return repository.findAll().stream()
        .map(countryMapperBuilder.dtoMapper()::dto)
        .toList();
  }
}
