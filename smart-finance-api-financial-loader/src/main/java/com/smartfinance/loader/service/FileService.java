package com.smartfinance.loader.service;

import com.smartfinance.client.template.model.Template;
import com.smartfinance.client.template.service.TemplateClient;
import com.smartfinance.config.AsyncConfig;
import com.smartfinance.loader.component.DecomposeLine;
import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.entity.FileLineStatus;
import com.smartfinance.loader.entity.FileStatus;
import com.smartfinance.loader.entity.Line;
import com.smartfinance.loader.exception.NotFoundException;
import com.smartfinance.loader.mq.model.Transact;
import com.smartfinance.loader.mq.model.TriggerTransact;
import com.smartfinance.loader.mq.provider.LoaderProvider;
import com.smartfinance.loader.repository.FileRepository;
import com.smartfinance.loader.repository.LineRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {
  private final LineRepository repository;
  private final LoaderProvider sender;
  private final FileRepository fileRepository;
  private final TemplateClient templateClient;
  private final DecomposeLine decompose;

  @Async(AsyncConfig.EXEC_NAME)
  @Transactional
  public void load(File entity, UUID template) {
    InputStream inputStream = new ByteArrayInputStream(entity.getData());
    BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream));

    List<Line> lines = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger();
    buffReader.lines()
        .forEach(line -> lines.add(Line.builder()
            .lineNumber(counter.incrementAndGet())
            .line(line)
            .file(entity)
            .build())
        );

    lines.forEach(line ->
        saveLine(entity, line, skipLines(getTemplate(template), lines)));

    sender.triggerTransact(fileRepository.getReferenceById(entity.getId()), template);
  }

  private List<Integer> skipLines(Template template, List<Line> lines) {
    return template.getLayout().getIgnoreLines().stream()
        .map(ignore -> {
          if ("top-down".equalsIgnoreCase(ignore.getStarts())) {
            return ignore.getLine();
          }
          return lines.size() - ignore.getLine();
        })
        .toList();
  }

  private void saveLine(File file, Line line, List<Integer> toSkip) {
    log.debug("Line: [{}][{}]", line.getLineNumber(), line.getLine());

    if (toSkip.stream().anyMatch(skip -> skip == line.getLineNumber())) {
      log.warn("Line {} skipped", line.getLineNumber());
      return;
    }

    file.addLine(repository.saveAndFlush(line));
    log.info("Line {} saved ID[{}]", line.getLineNumber(), line.getId());
  }

  @Transactional
  public void trigger(TriggerTransact trigger) {
    File file = getFile(trigger.uuid(), trigger.getFileName());
    Template template = getTemplate(trigger.template());
    file.getLines()
        .forEach(line -> send(line, template, trigger));
  }

  private void send(Line line, Template template, TriggerTransact trigger) {
    Transact transact = decompose.lineToTransact(line, template, trigger);
    log.debug("Transact: {}", transact);
    sender.sendTransact(transact);
    log.info("Line {} sent to be transacted.", line.getId());
  }

  private File getFile(UUID reference, String...fileName) {
    return fileRepository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException(
                "File %s not found. Ref # %s".formatted(
                    fileName == null || fileName.length == 0
                        ? "" : fileName[0],
                    reference)
            )
        );
  }

  @Transactional
  public void response(Transact transact) {
    Line line = getLine(transact.getLine().getId());

    line.setStatus(FileLineStatus.valueOf(transact.getResponse().getStatus()));
    if (FileLineStatus.FAIL_TO_LOAD.equals(line.getStatus())
        || FileLineStatus.DUPLICATED.equals(line.getStatus())) {
      line.setReasonFail(transact.getResponse().getMessage());
    }
    repository.saveAndFlush(line);
    
    completeFile(transact.getFile().getReference());
  }

  private void completeFile(UUID reference) {
    List<FileLineStatus> statuses = repository.allCompleted(reference);
    if (statuses.stream().anyMatch(FileLineStatus.IMPORTED::equals)) {
      return;
    }

    File file = getFile(reference);
    if (statuses.stream().anyMatch(FileLineStatus.FAIL_TO_LOAD::equals)) {
      file.setStatus(FileStatus.FAIL_TO_LOAD);
    } else if (statuses.stream().anyMatch(FileLineStatus.DUPLICATED::equals)) {
      file.setStatus(FileStatus.WARNING);
    } else {
      file.setStatus(FileStatus.LOADED);
    }

    fileRepository.saveAndFlush(file);
  }

  private Template getTemplate(UUID reference) {
    return templateClient.get(reference);
  }

  private Line getLine(long id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException(
            "Line ID[%s] not found".formatted(id)));
  }
}
