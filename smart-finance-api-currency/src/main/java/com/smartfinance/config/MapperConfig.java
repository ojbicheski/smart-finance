package com.smartfinance.config;

import com.smartfinance.currency.dto.CurrencyDTO;
import com.smartfinance.exchange.dto.ExchangeDTO;
import com.smartfinance.exchange.entities.Exchange;
import com.smartfinance.shared.entity.Country;
import com.smartfinance.currency.entity.Currency;
import com.smartfinance.shared.dto.CountryDTO;
import com.smartfinance.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public static Mapper<Currency, CurrencyDTO> currencyMapper() {
    return new Mapper<>(Currency.class, CurrencyDTO.class);
  }
  @Bean
  public static Mapper<Country, CountryDTO> countryMapper() {
    return new Mapper<>(Country.class, CountryDTO.class);
  }
  @Bean
  public static Mapper<Exchange, ExchangeDTO> exchangeMapper() {
    return new Mapper<>(Exchange.class, ExchangeDTO.class);
  }
}
