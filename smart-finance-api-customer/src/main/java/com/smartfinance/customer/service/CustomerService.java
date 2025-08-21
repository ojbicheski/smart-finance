package com.smartfinance.customer.service;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.exception.NotFoundException;
import com.smartfinance.customer.dto.CustomerDTO;
import com.smartfinance.mapper.Mapper;
import com.smartfinance.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {
  private final CustomerRepository repository;
  private final Mapper.Builder<Customer, CustomerDTO> customerMapperBuilder;

  public Customer find(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Customer not found"));
  }

  @Transactional
  public CustomerDTO save(CustomerDTO customerDTO) {
    return customerMapperBuilder.dtoMapper().dto(
        repository.save(customerMapperBuilder.entityMapper().entity(customerDTO))
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(find(reference));
  }

  public CustomerDTO get(UUID reference) {
    return customerMapperBuilder.dtoMapper().dto(find(reference));
  }

  public Page<CustomerDTO> list(String name, Pageable pageable) {
    if (name == null || name.isBlank()) {
      return repository.findAll(pageable).map(customerMapperBuilder.dtoMapper()::dto);
    }
    return repository.findAllByNameContaining(name, pageable)
        .map(customerMapperBuilder.dtoMapper()::dto);
  }
}
