package com.smartfinance.loader.controller.v1;

import com.smartfinance.loader.controller.CommonController;
import com.smartfinance.loader.dto.FileDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UploadController extends CommonController {
  ResponseEntity<FileDTO> upload(
      @RequestHeader(name = "x-sf-account-ref") UUID account,
      @RequestHeader(name = "x-sf-template-ref") UUID template,
      @RequestParam("file") MultipartFile file);
}
