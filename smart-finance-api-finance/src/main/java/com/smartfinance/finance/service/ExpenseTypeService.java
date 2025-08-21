package com.smartfinance.finance.service;

import com.smartfinance.finance.dto.expense.ExpenseTypeDTO;
import com.smartfinance.finance.entity.expense.ExpenseType;
import com.smartfinance.finance.entity.expense.Group;
import com.smartfinance.finance.exception.NotFoundException;
import com.smartfinance.finance.repository.ExpenseTypeRepository;
import com.smartfinance.finance.repository.GroupRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ExpenseTypeService {
  private final ExpenseTypeRepository repository;
  private final GroupRepository incomenTypeRepository;
  public final Mapper.Builder<ExpenseType, ExpenseTypeDTO> expenseTypeMapperBuilder;

  public ExpenseType get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Group not found"));
  }

  public ExpenseTypeDTO find(UUID reference) {
    return expenseTypeMapperBuilder.dtoMapper().dto(get(reference));
  }

  @Transactional
  public List<ExpenseTypeDTO> search(ExpenseTypeDTO dto) {
    ExpenseType entity = expenseTypeMapperBuilder.entityMapper().entity(dto);
    return repository.search(entity).stream()
        .map(type -> expenseTypeMapperBuilder.dtoMapper().dto(type))
        .toList();
  }

  @Transactional
  public List<ExpenseTypeDTO> list(UUID group, boolean active) {
    return repository.findByGroupAndActive(group, active).stream()
        .map(type -> expenseTypeMapperBuilder.dtoMapper().dto(type))
        .toList();
  }

  @Transactional
  public ExpenseTypeDTO save(UUID group, ExpenseTypeDTO dto) {
    ExpenseType entity = expenseTypeMapperBuilder.entityMapper().entity(dto);

    if (entity.isUpdate()) {
      entity = get(dto.getReference());
      entity.setName(dto.getName());
      entity.setDescription(dto.getDescription());
    } else {
      entity.setGroup(group(group));
      entity.activate();
    }

    entity = repository.saveAndFlush(entity);
    return expenseTypeMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(get(reference));
  }

  @Transactional
  public ExpenseTypeDTO activate(UUID reference) {
    ExpenseType entity = get(reference);
    entity.activate();
    return expenseTypeMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  @Transactional
  public ExpenseTypeDTO deactivate(UUID reference) {
    ExpenseType entity = get(reference);
    entity.deactivate();
    return expenseTypeMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  private Group group(UUID reference) {
    return incomenTypeRepository
        .findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Group not found"));
  }
}
