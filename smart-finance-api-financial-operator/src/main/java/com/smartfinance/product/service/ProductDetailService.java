package com.smartfinance.product.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.product.dto.ProductDetailDTO;
import com.smartfinance.product.entity.ProductDetail;
import com.smartfinance.product.repository.ProductDetailRepository;
import com.smartfinance.template.exception.ConflictException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductDetailService {
  private final ProductDetailRepository repository;
  private final Mapper.Builder<ProductDetail, ProductDetailDTO> productDetailMapperBuilder;
  private final ProductService productService;

  public ProductDetail get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Product Detail not found"));
  }

  @Transactional
  public ProductDetailDTO save(ProductDetailDTO dto) {
    ProductDetail entity = productDetailMapperBuilder.entityMapper().entity(dto);
    entity.setProduct(productService.get(dto.getProduct().getReference()));
    entity.activate();

    entity = repository.saveAndFlush(entity);
    return productDetailMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public ProductDetailDTO find(UUID reference) {
    return productDetailMapperBuilder.dtoMapper().dto(get(reference));
  }

  public List<ProductDetailDTO> list(UUID operator, boolean active) {
    return repository.findByProductAndActive(operator, active).stream()
        .map(productDetailMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    try {
      repository.delete(get(reference));
      repository.flush();
    } catch (ConstraintViolationException | DataIntegrityViolationException e) {
      throw new ConflictException("Product Detail is being used by another record. Delete can't be executed.", e);
    }
  }
}
