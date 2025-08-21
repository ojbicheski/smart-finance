package com.smartfinance.currency.service;

import com.smartfinance.currency.entities.CurrencyEntity;
import com.smartfinance.currency.exceptions.NotFoundException;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExchangeService {
  private final CurrencyRepository repository;
  private final Mapper<CurrencyEntity, Currency> currencyMapper;

  @Transactional
  public Currency save(Currency currency) {
    return currencyMapper.toDto(
        repository.save(currencyMapper.toEntity(currency))
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(
        repository.findByReference(reference)
            .orElseThrow(() -> new NotFoundException("Currency not found"))
    );
  }

  public Currency find(UUID reference) {
    return repository.findByReference(reference)
        .map(currencyMapper::toDto)
        .orElseThrow(() -> new NotFoundException("Currency not found"));
  }

  public Page<Currency> list(String name, Pageable pageable) {
    if (name == null || name.isBlank()) {
      return repository.findAll(pageable).map(currencyMapper::toDto);
    }
    return repository.findAllByNameContaining(name, pageable)
        .map(currencyMapper::toDto);
  }
}
