package com.smartfinance.loader.controller.v1;

import com.smartfinance.loader.controller.CommonController;
import com.smartfinance.loader.dto.FileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileController extends CommonController {
  ResponseEntity<FileDTO> get(
      @PathVariable UUID reference);
  List<FileDTO> list(
      @RequestHeader(name = "x-sf-account-ref") UUID account);
  ResponseEntity<Void> reprocess(
      @PathVariable UUID reference);
}
