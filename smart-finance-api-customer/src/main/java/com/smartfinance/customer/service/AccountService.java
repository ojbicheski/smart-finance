package com.smartfinance.customer.service;

import com.smartfinance.customer.dto.AccountTypeDTO;
import com.smartfinance.customer.entity.AccountType;
import com.smartfinance.customer.exception.NotFoundException;
import com.smartfinance.customer.repository.AccountTypeRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
  private final AccountTypeRepository repository;
  private final Mapper.Builder<AccountType, AccountTypeDTO> accountTypeMapperBuilder;

  public AccountType find(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Account Type not found"));
  }

  @Transactional
  public AccountTypeDTO save(AccountTypeDTO dto) {
    AccountType entity = accountTypeMapperBuilder.entityMapper().entity(dto);
    entity.activate();

    entity = repository.saveAndFlush(entity);
    return accountTypeMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public AccountTypeDTO get(UUID reference) {
    return accountTypeMapperBuilder.dtoMapper().dto(find(reference));
  }

  public List<AccountTypeDTO> list() {
    return repository.findAll().stream()
        .map(accountTypeMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(find(reference));
  }
}
