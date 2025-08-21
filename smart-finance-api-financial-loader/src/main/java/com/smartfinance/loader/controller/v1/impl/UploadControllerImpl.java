package com.smartfinance.loader.controller.v1.impl;

import com.smartfinance.loader.controller.v1.UploadController;
import com.smartfinance.loader.dto.FileDTO;
import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.service.FileService;
import com.smartfinance.loader.service.UploadService;
import com.smartfinance.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class UploadControllerImpl implements UploadController {
  private final UploadService uploadService;
  private final FileService fileService;
  private final Mapper.Builder<File, FileDTO> fileMapperBuilder;

  @PostMapping("/upload")
  @Override
  public ResponseEntity<FileDTO> upload(UUID account, UUID template, MultipartFile file) {
    File entity = uploadService.upload(account, file);
    fileService.load(entity, template);
    return ResponseEntity.ok(fileMapperBuilder.entityMapper().dto(entity));
  }
}
