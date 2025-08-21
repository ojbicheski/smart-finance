package com.smartfinance.loader.controller.v1.impl;

import com.smartfinance.loader.controller.v1.FileController;
import com.smartfinance.loader.dto.FileDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class FileControllerImpl implements FileController {
  @Override
  public ResponseEntity<FileDTO> get(UUID reference) {
    return null;
  }

  @Override
  public List<FileDTO> list(UUID account) {
    return List.of();
  }

  @Override
  public ResponseEntity<Void> reprocess(UUID reference) {
    return null;
  }
}
