package com.smartfinance.template.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.operator.service.OperatorService;
import com.smartfinance.template.dto.FormatDTO;
import com.smartfinance.template.entity.Format;
import com.smartfinance.template.exception.ConflictException;
import com.smartfinance.template.repository.FormatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FormatService {
  private final FormatRepository repository;
  private final Mapper.Builder<Format, FormatDTO> formatMapperBuilder;
  private final OperatorService operatorService;

  public Format get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Format not found"));
  }

  @Transactional
  public FormatDTO save(UUID operator, FormatDTO dto) {
    Format entity = formatMapperBuilder.entityMapper().entity(dto);
    entity.activate();
    entity.setOperator(operatorService.get(operator));

    entity = repository.saveAndFlush(entity);
    return formatMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public FormatDTO find(UUID reference) {
    return formatMapperBuilder.dtoMapper().dto(get(reference));
  }

  public List<FormatDTO> list(UUID operator, boolean active) {
    return repository.findByOperatorAndActive(operator, active).stream()
        .map(formatMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    try {
      repository.delete(get(reference));
    } catch (ConstraintViolationException e) {
      throw new ConflictException("Format is being used by a Template. Delete can't be executed.", e);
    }
  }
}
