package com.smartfinance.finance.service;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.finance.dto.income.IncomeTypeDTO;
import com.smartfinance.finance.entity.income.IncomeType;
import com.smartfinance.finance.exception.NotFoundException;
import com.smartfinance.finance.repository.IncomeTypeRepository;
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
public class IncomeTypeService {
  private final IncomeTypeRepository repository;
  private final CustomerRepository customerRepository;
  public final Mapper.Builder<IncomeType, IncomeTypeDTO> incomeTypeMapperBuilder;

  public IncomeType get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Income Type not found"));
  }

  public IncomeTypeDTO find(UUID reference) {
    return incomeTypeMapperBuilder.dtoMapper().dto(get(reference));
  }

  @Transactional
  public List<IncomeTypeDTO> search(IncomeTypeDTO dto) {
    IncomeType entity = incomeTypeMapperBuilder.entityMapper().entity(dto);
    return repository.search(entity).stream()
        .map(IncomeType -> incomeTypeMapperBuilder.dtoMapper().dto(IncomeType))
        .toList();
  }

  @Transactional
  public List<IncomeTypeDTO> list(UUID customer, boolean active) {
    return repository.findByCustomerAndActive(customer, active).stream()
        .map(IncomeType -> incomeTypeMapperBuilder.dtoMapper().dto(IncomeType))
        .toList();
  }

  @Transactional
  public IncomeTypeDTO save(UUID customer, IncomeTypeDTO dto) {
    IncomeType entity = incomeTypeMapperBuilder.entityMapper().entity(dto);

    if (entity.isUpdate()) {
      entity = get(dto.getReference());
      entity.setName(dto.getName());
      entity.setDescription(dto.getDescription());
    } else {
      entity.setCustomer(customer(customer));
      entity.activate();
    }

    entity = repository.saveAndFlush(entity);
    return incomeTypeMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(get(reference));
  }

  @Transactional
  public IncomeTypeDTO activate(UUID reference) {
    IncomeType entity = get(reference);
    entity.activate();
    return incomeTypeMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  @Transactional
  public IncomeTypeDTO deactivate(UUID reference) {
    IncomeType entity = get(reference);
    entity.deactivate();
    return incomeTypeMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  private Customer customer(UUID reference) {
    return customerRepository
        .findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Customer not found"));
  }
}
