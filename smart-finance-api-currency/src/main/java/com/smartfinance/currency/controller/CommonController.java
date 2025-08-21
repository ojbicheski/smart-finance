package com.smartfinance.currency.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface CommonController {
  default PageRequest page(int page, int size, String orderBy, String direction) {
    return PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
  }
}
