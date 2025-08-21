package com.smartfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.smartfinance.customer.repository",
    "com.smartfinance.operator.repository",
    "com.smartfinance.currency.repository",
    "com.smartfinance.preference.repository",
    "com.smartfinance.shared.repository",
})
@EnableTransactionManagement
public class JPAConfig {
}
