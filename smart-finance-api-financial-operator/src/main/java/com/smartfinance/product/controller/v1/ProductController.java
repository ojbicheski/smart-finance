package com.smartfinance.product.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.operator.dto.CountryDTO;

import java.util.List;

public interface ProductController extends CommonController {
  List<CountryDTO> list();
}
