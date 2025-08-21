package com.smartfinance.loader.component;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import com.smartfinance.client.template.model.Template;
import com.smartfinance.loader.entity.Line;
import com.smartfinance.loader.mq.model.Transact;
import com.smartfinance.loader.mq.model.TriggerTransact;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class DecomposeLine {
  private static final String DATE = "Transact.Date";
  private static final String NAME = "Transact.Name";
  private static final String DESCRIPTION = "Transact.Description";
  private static final String TYPE = "Transact.Type";
  private static final String VALUE = "Transact.Value";

  private final Context.Builder jsContext;

  private Invocable function(String script) throws ScriptException {
    GraalJSScriptEngine engine = GraalJSScriptEngine.create(null,jsContext);
    engine.eval(script);
    return engine;
  }

  public Transact lineToTransact(Line line, Template template, TriggerTransact trigger) {
    return Transact.builder()
        .line(line)
        .trigger(trigger)
        .event(Transact.Event.builder()
            .date(load(line, position(DATE, template), LocalDate.class))
            .name(load(line, position(NAME, template), String.class))
            .description(load(line, position(DESCRIPTION, template), String.class))
            .type(load(line, position(TYPE, template), String.class))
            .amount(load(line, position(VALUE, template), Float.class))
            .build())
        .build();
  }

  private <T> T load(Line line, Template.Position position, Class<T> klass) {
    Object value = of(line.value(position.getSequence()), position);
    if (Objects.nonNull(position.getFunction())) {
      try {
        value = function(position.getFunction())
            .invokeFunction("call", value);
      } catch (ScriptException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
    if (klass == Float.class && value instanceof Double) {
      return klass.cast(((Double) value).floatValue());
    }
    return klass.cast(value);
  }

  private Object of(String value, Template.Position position) {
    if (Arrays.stream(position.getRemoveCharacters()).anyMatch(value::contains)) {
      for(String toReplace : position.getRemoveCharacters()) {
        value = value.replace(toReplace, "");
      }
    }
    value = value.trim();

    return switch (position.getType()) {
      case "DATE" -> LocalDate.parse(value,
          DateTimeFormatter.ofPattern(position.getFormat()));
      case "DECIMAL" -> Float.valueOf(value);
      default -> value;
    };
  }

  private Template.Position position(String mapTo, Template template) {
    return template.getLayout().getPositions().stream()
        .filter(p -> mapTo.equalsIgnoreCase(p.getMapTo()))
        .findFirst()
        .orElseThrow();
  }
}
