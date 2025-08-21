package com.smartfinance.loader.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartfinance.customer.dto.AccountDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class FileDTO {
  private UUID reference;
  private String fileName;
  private String status;
  private AccountDTO account;
  private List<LineDTO> lines;
}
