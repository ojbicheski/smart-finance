package com.smartfinance.finance.service;

import com.smartfinance.finance.dto.expense.GroupDTO;
import com.smartfinance.finance.entity.expense.Group;
import com.smartfinance.finance.exception.NotFoundException;
import com.smartfinance.finance.repository.GroupRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ExpenseService {
  private final GroupRepository repository;
  public final Mapper.Builder<Group, GroupDTO> groupMapperBuilder;

  public Group find(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Group not found"));
  }

  @Transactional
  public GroupDTO save(GroupDTO dto) {
    Group entity = groupMapperBuilder.entityMapper().entity(dto);

    return groupMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }
}
