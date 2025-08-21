package com.smartfinance.operator.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.exception.BadRequestException;
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
public class OperatorService {
  private final OperatorRepository repository;
  private final Mapper.Builder<Operator, OperatorDTO> operatorMapperBuilder;
  private final CountryService countryService;

  public Operator get(UUID reference) {
    if (reference == null) {
      throw new BadRequestException("Operator reference is invalid.");
    }

    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Operator not found"));
  }

  @Transactional
  public OperatorDTO save(UUID country, OperatorDTO dto) {
    Operator entity = operatorMapperBuilder.entityMapper().entity(dto);

    if (entity.isUpdate()) {
      entity = get(entity.getReference());

      entity.setSwiftCode(dto.getSwiftCode());
      entity.setName(dto.getName());
      entity.setDisplay(dto.getDisplay());
    } else {
      entity.activate();
      entity.setCountry(countryService.get(country));
    }

    entity = repository.saveAndFlush(entity);
    return operatorMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public OperatorDTO find(UUID reference) {
    return operatorMapperBuilder.dtoMapper().dto(get(reference));
  }

  public void delete(UUID reference) {
    repository.delete(get(reference));
  }

  public List<OperatorDTO> list(UUID country) {
    return repository.findByCountry(country).stream()
        .map(operatorMapperBuilder.dtoMapper()::dto)
        .toList();
  }
}
