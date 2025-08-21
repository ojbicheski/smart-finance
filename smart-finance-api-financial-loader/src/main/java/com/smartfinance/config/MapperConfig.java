package com.smartfinance.config;

import com.smartfinance.loader.dto.FileDTO;
import com.smartfinance.loader.dto.LineDTO;
import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.entity.Line;
import com.smartfinance.mapper.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public static Mapper.Builder<File, FileDTO> fileMapperBuilder() {
    return new Mapper.Builder<>(File.class, FileDTO.class);
  }
  @Bean
  public Mapper.Builder<Line, LineDTO> fileLineMapperBuilder() {
    return new Mapper.Builder<>(Line.class, LineDTO.class);
  }
}
