package com.smartfinance.config;

import com.smartfinance.finance.dto.expense.ExpenseTypeDTO;
import com.smartfinance.finance.dto.income.IncomeTypeDTO;
import com.smartfinance.finance.entity.expense.ExpenseType;
import com.smartfinance.finance.entity.income.IncomeType;
import com.smartfinance.mapper.Mapper;
import com.smartfinance.finance.dto.expense.GroupDTO;
import com.smartfinance.finance.entity.expense.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public Mapper.Builder<Group, GroupDTO> groupMapperBuilder() {
    return new Mapper.Builder<>(Group.class, GroupDTO.class);
  }
  @Bean
  public Mapper.Builder<ExpenseType, ExpenseTypeDTO> expenseTypeMapperBuilder() {
    return new Mapper.Builder<>(ExpenseType.class, ExpenseTypeDTO.class);
  }
  @Bean
  public Mapper.Builder<IncomeType, IncomeTypeDTO> incomeTypeMapperBuilder() {
    return new Mapper.Builder<>(IncomeType.class, IncomeTypeDTO.class);
  }
}
