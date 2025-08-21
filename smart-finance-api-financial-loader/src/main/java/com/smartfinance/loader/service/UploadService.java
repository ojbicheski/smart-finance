package com.smartfinance.loader.service;

import com.smartfinance.loader.dto.FileDTO;
import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.exception.NotFoundException;
import com.smartfinance.loader.repository.AccountRepository;
import com.smartfinance.loader.repository.FileRepository;
import com.smartfinance.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadService {
  private final FileRepository fileRepository;
  private final AccountRepository accountRepository;
  private final Mapper<File, FileDTO> fileMapper;

  public FileDTO upload(UUID account, MultipartFile file) {
    File entity = File.builder()
        .file(file)
        .account(accountRepository.findByReference(account)
            .orElseThrow(() -> new NotFoundException("Customer Account not found.")))
        .build();

    return fileMapper.toDto(fileRepository.save(entity));
  }

//  @Transactional
//  public FileDTO save(FileDTO fileDTO) {
//    return fileMapper.toDto(
//        repository.save(fileMapper.toEntity(fileDTO))
//    );
//  }
//
//  @Transactional
//  public void delete(UUID reference) {
//    repository.delete(
//        repository.findByReference(reference)
//            .orElseThrow(() -> new NotFoundException("File not found"))
//    );
//  }
//
//  public FileDTO find(UUID reference) {
//    return repository.findByReference(reference)
//        .map(fileMapper::toDto)
//        .orElseThrow(() -> new NotFoundException("File not found"));
//  }
//
//  public Page<FileDTO> list(String name, Pageable pageable) {
//    if (name == null || name.isBlank()) {
//      return repository.findAll(pageable).map(fileMapper::toDto);
//    }
//    return repository.findAllByNameContaining(name, pageable)
//        .map(fileMapper::toDto);
//  }
}
