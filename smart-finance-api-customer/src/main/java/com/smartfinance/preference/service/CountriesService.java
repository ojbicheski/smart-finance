package com.smartfinance.preference.service;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.exception.NotFoundException;
import com.smartfinance.customer.service.CustomerService;
import com.smartfinance.mapper.Mapper;
import com.smartfinance.preference.entity.Countries;
import com.smartfinance.preference.repository.CountriesRepository;
import com.smartfinance.shared.dto.CountryDTO;
import com.smartfinance.shared.entity.Country;
import com.smartfinance.shared.repository.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CountriesService {
  private final CountriesRepository repository;
  private final CountryRepository countryRepository;
  private final CustomerService customerService;
  private final Mapper.Builder<Country, CountryDTO> countryMapperBuilder;

  public List<CountryDTO> getCountries(UUID customer) {
    return repository.findByCustomer(customerService.find(customer)).stream()
        .map(Countries::getCountry)
        .map(e -> countryMapperBuilder.dtoMapper().dto(e))
        .toList();
  }

  @Transactional
  public void save(UUID customerRef, List<CountryDTO> countries) {
    Customer customer = customerService.find(customerRef);
    repository.deleteByCustomer(customer);
    repository.flush();

    countries.forEach(country -> attach(customer, country.getReference()));
  }

  private void attach(Customer customer, UUID reference) {
    Country country = countryRepository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException(
            "Country %s not found".formatted(reference)));

    repository.saveAndFlush(Countries.builder()
            .country(country)
            .customer(customer)
        .build());
  }
}
