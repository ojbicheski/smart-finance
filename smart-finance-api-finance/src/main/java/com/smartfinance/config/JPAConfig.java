package com.smartfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.smartfinance.exchange.repository",
    "com.smartfinance.finance.repository",
    "com.smartfinance.currency.repository",
    "com.smartfinance.customer.repository",
    "com.smartfinance.transaction.repository",
})
@EnableTransactionManagement
public class JPAConfig {
}
