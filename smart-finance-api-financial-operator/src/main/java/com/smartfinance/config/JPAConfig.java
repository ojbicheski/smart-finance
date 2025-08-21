package com.smartfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {
    "com.smartfinance.operator.repository",
    "com.smartfinance.product.repository",
    "com.smartfinance.template.repository"
})
@EnableTransactionManagement
public class JPAConfig {
}
