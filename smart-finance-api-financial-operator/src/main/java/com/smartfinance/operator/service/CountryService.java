package com.smartfinance.operator.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.operator.repository.CountryRepository;
import com.smartfinance.operator.repository.OperatorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CountryService {
  private final OperatorRepository repository;
  private final Mapper<Operator, OperatorDTO> operatorMapper;
  private final CountryRepository countryRepository;

  public Operator get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Operator not found"));
  }

  @Transactional
  public OperatorDTO save(UUID country, OperatorDTO dto) {
    Operator entity = operatorMapper.toEntity(dto);
    entity.activate();
    entity.setCountry(countryRepository.findByReference(country));

    entity = repository.saveAndFlush(entity);
    return operatorMapper.toDto(
        repository.getReferenceById(entity.getId())
    );
  }

  public OperatorDTO find(UUID reference) {
    return operatorMapper.toDto(get(reference));
  }

  public List<OperatorDTO> list(UUID country) {
    return repository.findByCountry(country).stream()
        .map(operatorMapper::toDto)
        .toList();
  }
}
