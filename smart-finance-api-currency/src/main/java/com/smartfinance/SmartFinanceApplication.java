package com.smartfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SmartFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartFinanceApplication.class, args);
	}

}
