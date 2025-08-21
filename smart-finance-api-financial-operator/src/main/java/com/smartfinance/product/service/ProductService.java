package com.smartfinance.product.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.exception.NotFoundException;
import com.smartfinance.product.dto.ProductDTO;
import com.smartfinance.product.entity.Product;
import com.smartfinance.product.repository.ProductRepository;
import com.smartfinance.template.exception.ConflictException;
import com.smartfinance.template.service.TemplateService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository repository;
  private final Mapper.Builder<Product, ProductDTO> productMapperBuilder;
  private final TypeService typeService;
  private final TemplateService templateService;

  public Product get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Product not found"));
  }

  @Transactional
  public ProductDTO save(ProductDTO dto) {
    Product entity = productMapperBuilder.entityMapper().entity(dto);
    entity.setType(typeService.get(dto.getType().getReference()));
    entity.setTemplate(templateService.get(dto.getTemplate().getReference()));
    entity.activate();

    entity = repository.saveAndFlush(entity);
    return productMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public ProductDTO find(UUID reference) {
    return productMapperBuilder.dtoMapper().dto(get(reference));
  }

  public List<ProductDTO> list(UUID operator, boolean active) {
    return repository.findByOperatorAndActive(operator, active).stream()
        .map(productMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    try {
      repository.delete(get(reference));
      repository.flush();
    } catch (ConstraintViolationException | DataIntegrityViolationException e) {
      throw new ConflictException("Product is being used by another record. Delete can't be executed.", e);
    }
  }
}
