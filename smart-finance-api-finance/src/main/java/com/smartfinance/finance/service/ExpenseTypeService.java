package com.smartfinance.finance.service;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.finance.dto.expense.GroupDTO;
import com.smartfinance.finance.entity.expense.Group;
import com.smartfinance.finance.exception.NotFoundException;
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
  private final GroupRepository repository;
  private final CustomerRepository customerRepository;
  public final Mapper.Builder<Group, GroupDTO> groupMapperBuilder;

  public Group get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Group not found"));
  }

  public GroupDTO find(UUID reference) {
    return groupMapperBuilder.dtoMapper().dto(get(reference));
  }

  @Transactional
  public List<GroupDTO> search(GroupDTO dto) {
    Group entity = groupMapperBuilder.entityMapper().entity(dto);
    return repository.search(entity).stream()
        .map(group -> groupMapperBuilder.dtoMapper().dto(group))
        .toList();
  }

  @Transactional
  public List<GroupDTO> list(UUID customer, boolean active) {
    return repository.findByCustomerAndActive(customer, active).stream()
        .map(group -> groupMapperBuilder.dtoMapper().dto(group))
        .toList();
  }

  @Transactional
  public GroupDTO save(UUID customer, GroupDTO dto) {
    Group entity = groupMapperBuilder.entityMapper().entity(dto);

    if (entity.isUpdate()) {
      entity = get(dto.getReference());
      entity.setName(dto.getName());
      entity.setDescription(dto.getDescription());
    } else {
      entity.setCustomer(customer(customer));
      entity.activate();
    }

    entity = repository.saveAndFlush(entity);
    return groupMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(get(reference));
  }

  @Transactional
  public GroupDTO activate(UUID reference) {
    Group entity = get(reference);
    entity.activate();
    return groupMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  @Transactional
  public GroupDTO deactivate(UUID reference) {
    Group entity = get(reference);
    entity.deactivate();
    return groupMapperBuilder.dtoMapper().dto(
        repository.saveAndFlush(entity)
    );
  }

  private Customer customer(UUID reference) {
    return customerRepository
        .findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Customer not found"));
  }
}
