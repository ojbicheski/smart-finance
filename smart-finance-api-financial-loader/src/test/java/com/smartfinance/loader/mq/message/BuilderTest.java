package com.smartfinance.loader.mq.message;

import com.smartfinance.loader.mq.model.Transact;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.amqp.core.Message;

import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {
  @ParameterizedTest
  @MethodSource("messageToObjectConvert")
  @SneakyThrows
  <T> void shouldMessageToObjectConvert(String jsonPath, Class<T> klass) {
    InputStream in = BuilderTest.class.getResourceAsStream(jsonPath);
    assertNotNull(in);
    assertNotNull(Builder.messageToObject(new Message(in.readAllBytes()), klass));
  }
  private static Stream<Arguments> messageToObjectConvert() {
    return Stream.of(
        Arguments.of(
            "/com/smartfinance/loader/mq/message/loaded-mq.transact.response.json",
            Transact.class
        )
    );
  }
}
