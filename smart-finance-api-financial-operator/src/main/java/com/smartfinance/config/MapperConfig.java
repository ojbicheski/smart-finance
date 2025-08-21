package com.smartfinance.config;

import com.smartfinance.loader.dto.FileDTO;
import com.smartfinance.loader.entity.File;
import com.smartfinance.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public static Mapper<File, FileDTO> fileMapper() {
    return new Mapper<>(File.class, FileDTO.class);
  }
}
