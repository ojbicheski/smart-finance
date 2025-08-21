package com.smartfinance.customer.config;

import com.smartfinance.customer.dto.AccountTypeDTO;
import com.smartfinance.customer.entity.AccountType;
import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.dto.CustomerDTO;
import com.smartfinance.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public Mapper.Builder<Customer, CustomerDTO> customerMapper() {
    return new Mapper.Builder<>(Customer.class, CustomerDTO.class);
  }
  @Bean
  public Mapper.Builder<AccountType, AccountTypeDTO> accountTypeMapperBuilder() {
    return new Mapper.Builder<>(AccountType.class, AccountTypeDTO.class);
  }
}
