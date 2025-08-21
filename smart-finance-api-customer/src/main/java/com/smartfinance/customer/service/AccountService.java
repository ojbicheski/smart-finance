package com.smartfinance.customer.service;

import com.smartfinance.currency.dto.CurrencyDTO;
import com.smartfinance.currency.entity.Currency;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.customer.dto.AccountDTO;
import com.smartfinance.customer.entity.Account;
import com.smartfinance.customer.entity.AccountTemplate;
import com.smartfinance.customer.exception.NotFoundException;
import com.smartfinance.customer.repository.AccountRepository;
import com.smartfinance.customer.repository.AccountTemplateRepository;
import com.smartfinance.mapper.Mapper;
import com.smartfinance.operator.dto.ProductDetailDTO;
import com.smartfinance.operator.entity.product.ProductDetail;
import com.smartfinance.operator.repository.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
  private final AccountRepository repository;
  private final AccountTemplateRepository templateRepository;
  private final CustomerService customerService;
  private final AccountTypeService typeService;
  private final ProductDetailRepository detailRepository;
  private final CurrencyRepository currencyRepository;
  private final Mapper.Builder<Account, AccountDTO> accountMapperBuilder;

  public Account find(UUID reference) {
    return repository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Account not found"));
  }

  @Transactional
  public AccountDTO save(UUID customer, AccountDTO dto) {
    Account entity = accountMapperBuilder.entityMapper().entity(dto);
    entity.setCustomer(customerService.find(customer));
    entity.setType(typeService.find(dto.getType().getReference()));
    entity.setDetail(findDetail(dto.getDetail()));
    entity.setOperator(entity.getDetail().getProduct().getTemplate().getOperator());
    entity.setCurrency(findCurrency(dto.getCurrency()));
    entity.activate();

    entity = repository.saveAndFlush(entity);

    templateRepository.saveAndFlush(AccountTemplate.builder()
        .account(entity)
        .template(entity.getDetail().getProduct().getTemplate())
        .build());

    return accountMapperBuilder.dtoMapper().dto(
        repository.getReferenceById(entity.getId())
    );
  }

  private ProductDetail findDetail(ProductDetailDTO productDetail) {
    return detailRepository.findByReference(productDetail.getReference())
        .orElseThrow(() -> new NotFoundException("Product Detail not found"));
  }

  private Currency findCurrency(CurrencyDTO currency) {
    return currencyRepository.findByReference(currency.getReference())
        .orElseThrow(() -> new NotFoundException("Currency not found"));
  }

  public AccountDTO get(UUID reference) {
    return accountMapperBuilder.dtoMapper().dto(find(reference));
  }

  public List<AccountDTO> list(UUID customer, boolean active) {
    return repository.findByCustomerReferenceAndActive(customer, active).stream()
        .map(accountMapperBuilder.dtoMapper()::dto)
        .toList();
  }

  @Transactional
  public void activate(UUID reference) {
    Account entity = find(reference);
    entity.activate();
    repository.saveAndFlush(entity);
  }

  @Transactional
  public void deactivate(UUID reference) {
    Account entity = find(reference);
    entity.activate();
    repository.saveAndFlush(entity);
  }

  @Transactional
  public void delete(UUID reference) {
    repository.delete(find(reference));
  }
}
