package com.smartfinance.product.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.operator.service.OperatorService;
import com.smartfinance.product.dto.TypeDTO;
import com.smartfinance.product.entity.Type;
import com.smartfinance.product.repository.TypeRepository;
import com.smartfinance.template.dto.TemplateDTO;
import com.smartfinance.template.entity.Template;
import com.smartfinance.template.exception.ConflictException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TypeService {
  private final TypeRepository repository;
  private final Mapper.Builder<Type, TypeDTO> typeMapperBuilder;
  private final OperatorService operatorService;

  public Type get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Type not found"));
  }

  @Transactional
  public TypeDTO save(UUID operator, TypeDTO dto) {
    Type entity = typeMapperBuilder.entityMapper().entity(dto);
    entity.setOperator(operatorService.get(operator));
    entity.activate();

    entity = repository.saveAndFlush(entity);
    return typeMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public TypeDTO find(UUID reference) {
    return typeMapperBuilder.dtoMapper().dto(get(reference));
  }

  public List<TypeDTO> list(UUID operator, boolean active) {
    return repository.findByOperatorAndActive(operator, active).stream()
        .map(typeMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    try {
      repository.delete(get(reference));
      repository.flush();
    } catch (ConstraintViolationException | DataIntegrityViolationException e) {
      throw new ConflictException("Type is being used by another record. Delete can't be executed.", e);
    }
  }
}
