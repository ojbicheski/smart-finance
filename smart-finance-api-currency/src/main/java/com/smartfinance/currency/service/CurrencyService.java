package com.smartfinance.currency.service;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.currency.exception.NotFoundException;
import com.smartfinance.currency.dto.CurrencyDTO;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurrencyService {
  private final CurrencyRepository repository;
  private final Mapper<Currency, CurrencyDTO> currencyMapper;

  public Currency get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Currency not found"));
  }

  @Transactional
  public CurrencyDTO save(CurrencyDTO currencyDTO) {
    return currencyMapper.toDto(
        repository.save(currencyMapper.toEntity(currencyDTO))
    );
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(
        repository.findByReference(reference)
            .orElseThrow(() -> new NotFoundException("Currency not found"))
    );
  }

  public CurrencyDTO find(UUID reference) {
    return currencyMapper.toDto(get(reference));
  }

  public Page<CurrencyDTO> list(String name, Pageable pageable) {
    if (name == null || name.isBlank()) {
      return repository.findAll(pageable).map(currencyMapper::toDto);
    }
    return repository.findAllByNameContaining(name, pageable)
        .map(currencyMapper::toDto);
  }

  public List<Currency> list() {
    return repository.findAll();
  }

  public List<Currency> listByCountries(List<UUID> countries) {
    return repository.findByCountries(countries);
  }
}
