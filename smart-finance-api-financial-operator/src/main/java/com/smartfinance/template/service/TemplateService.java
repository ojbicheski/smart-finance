package com.smartfinance.template.service;

import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.service.OperatorService;
import com.smartfinance.template.dto.TemplateDTO;
import com.smartfinance.template.entity.Template;
import com.smartfinance.template.exception.ConflictException;
import com.smartfinance.template.exception.NotFoundException;
import com.smartfinance.template.repository.TemplateRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TemplateService {
  private final TemplateRepository repository;
  private final Mapper.Builder<Template, TemplateDTO> templateMapperBuilder;
  private final OperatorService operatorService;
  private final FormatService formatService;

  public Template get(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Template not found"));
  }

  @Transactional
  public TemplateDTO save(UUID operator, TemplateDTO dto) {
    Template entity = templateMapperBuilder.entityMapper().entity(dto);

    if (entity.isUpdate()) {
      Template.Layout layout = entity.getLayout();
      entity = get(entity.getReference());

      entity.setDescription(dto.getDescription());
      entity.setLayout(layout);
    } else {
      entity.setOperator(operatorService.get(operator));
      entity.setFormat(formatService.get(dto.getFormat().getReference()));
      entity.activate();
    }

    entity = repository.saveAndFlush(entity);
    return templateMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  public TemplateDTO find(UUID reference) {
    return templateMapperBuilder.dtoMapper().dto(get(reference));
  }

  public TemplateDTO findByOperator(UUID operator) {
    return repository.findByOperatorAndActive(operator, true).stream()
        .findFirst()
        .map(templateMapperBuilder.dtoMapper()::dto)
        .orElseThrow(() -> new NotFoundException("Template not found"));
  }

  public List<TemplateDTO> list(UUID operator, boolean active) {
    return repository.findByOperatorAndActive(operator, active).stream()
        .map(templateMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void delete(UUID reference) {
    try {
      repository.delete(get(reference));
    } catch (ConstraintViolationException e) {
      throw new ConflictException("Template is being used by another record. Delete can't be executed.", e);
    }
  }
}
