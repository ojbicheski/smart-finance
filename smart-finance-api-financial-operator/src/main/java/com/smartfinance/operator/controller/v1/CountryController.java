package com.smartfinance.operator.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.operator.dto.CountryDTO;

import java.util.List;

public interface CountryController extends CommonController {
  List<CountryDTO> list();
}
