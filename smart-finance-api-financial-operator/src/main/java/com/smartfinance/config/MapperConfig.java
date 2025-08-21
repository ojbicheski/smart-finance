package com.smartfinance.config;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.CountryDTO;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.entity.Country;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.product.dto.ProductDTO;
import com.smartfinance.product.dto.ProductDetailDTO;
import com.smartfinance.product.dto.TypeDTO;
import com.smartfinance.product.entity.Product;
import com.smartfinance.product.entity.ProductDetail;
import com.smartfinance.product.entity.Type;
import com.smartfinance.template.dto.FormatDTO;
import com.smartfinance.template.dto.TemplateDTO;
import com.smartfinance.template.entity.Format;
import com.smartfinance.template.entity.Template;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public Mapper.Builder<Template, TemplateDTO> templateMapperBuilder() {
    Mapper.Builder<Template, TemplateDTO> builder = Mapper.builder(Template.class, TemplateDTO.class);

    Converter<String, Template.PositionType> positionTypeConverter =
        ctx -> ctx.getSource() == null ? null : Template.PositionType.of(ctx.getSource());
    builder.mappingEntity(new PropertyMap<>() {
      @Override
      protected void configure() {
        // configuration to avoid attribute templateVersion be set to version
        skip(destination.getVersion());
      }
    });

    return builder;
  }
  @Bean
  public Mapper.Builder<Format, FormatDTO> formatMapperBuilder() {
    return Mapper.builder(Format.class, FormatDTO.class);
  }
  @Bean
  public Mapper.Builder<Operator, OperatorDTO> operatorMapperBuilder() {
    return Mapper.builder(Operator.class, OperatorDTO.class);
  }
  @Bean
  public Mapper.Builder<Country, CountryDTO> countryMapperBuilder() {
    return Mapper.builder(Country.class, CountryDTO.class);
  }
  @Bean
  public Mapper.Builder<Type, TypeDTO> typeMapperBuilder() {
    return Mapper.builder(Type.class, TypeDTO.class);
  }
  @Bean
  public Mapper.Builder<Product, ProductDTO> productMapperBuilder() {
    return Mapper.builder(Product.class, ProductDTO.class);
  }
  @Bean
  public Mapper.Builder<ProductDetail, ProductDetailDTO> productDetailMapperBuilder() {
    return Mapper.builder(ProductDetail.class, ProductDetailDTO.class);
  }
}
