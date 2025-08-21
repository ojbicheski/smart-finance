package com.smartfinance.loader.component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.client.template.model.Template;
import com.smartfinance.loader.config.JSEngineConfig;
import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.entity.Line;
import com.smartfinance.loader.mq.model.Transact;
import com.smartfinance.loader.mq.model.TriggerTransact;
import lombok.SneakyThrows;
import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(SpringExtension.class)
public class DecomposeLineTest {
  private static final String PATH = "/com/smartfinance/loader/component/";

  private final ObjectMapper om = new ObjectMapper();
  private final Context.Builder jsContext = new JSEngineConfig().jsContext();

  private Template template;
  private List<Line> lines;
  private TriggerTransact trigger;

  private DecomposeLine decomposeLine;

  @SneakyThrows
  @BeforeEach
  void setup() {
    if (template == null) {
      template = om.readValue(
          DecomposeLineTest.class.getResourceAsStream(
              PATH.concat("template-Scotiabank-Chequing_V-1.0_2017.json")),
          Template.class
      );
    }

    if (lines == null) {
      lines = new ArrayList<>();

      BufferedReader reader = new BufferedReader(new InputStreamReader(
          Objects.requireNonNull(DecomposeLineTest.class.getResourceAsStream(
              PATH.concat("data-Scotiabank-Chequing_V-1.0_2017.csv")))
      ));

      AtomicInteger counter = new AtomicInteger();
      String line;
      while ((line = reader.readLine()) != null) {
        // Process each line of the text file
        lines.add(Line.builder()
            .lineNumber(counter.getAndIncrement())
            .line(line)
            .file(new File())
            .build());
      }
    }

    if (trigger == null) {
      trigger = TriggerTransact.builder()
          .totalLines(lines.size())
          .fileName("data-Scotiabank-Chequing_V-1.0_2017.csv")
          .account(UUID.randomUUID().toString())
          .file(UUID.randomUUID().toString())
          .build();
    }

    decomposeLine = new DecomposeLine(jsContext);
  }

  @Test
  void shouldDecomposeLine() {
    List<Transact> transacts = new ArrayList<>();
    lines.forEach(line ->
      transacts.add(decomposeLine.lineToTransact(line, template, trigger)));

    AtomicReference<Float> credits = new AtomicReference<>(0f);
    AtomicReference<Float> debits = new AtomicReference<>(0f);

    transacts.stream()
        .map(Transact::getEvent)
        .forEach(event -> {
          if ("DEBIT".equalsIgnoreCase(event.getType())) {
            debits.updateAndGet(v -> v + event.getAmount());
          } else if ("CREDIT".equalsIgnoreCase(event.getType())) {
            credits.updateAndGet(v -> v + event.getAmount());
          }

          System.out.println(event.getName());
        });

    System.out.println(debits);
    System.out.println(credits);
  }
}
