package com.smartfinance.client.ofx.service;

import com.smartfinance.client.ofx.config.properties.OFXProperties;
import com.smartfinance.client.ofx.exception.InternalServerErrorException;
import com.smartfinance.client.ofx.model.OFX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@AllArgsConstructor
@Slf4j
public class OFXClient {
  private static final String ERROR_PERIOD = "Failed to load OFX. Period: %s, From: %s, To: %s";
  private static final String ERROR_RANGE = "Failed to load OFX. From: %s, To: %s, Start: %s, End: %s";

  @AllArgsConstructor
  public enum Period {
    WEEK("week"), MONTH("month"), THREE_MONTH("3month"),
    SIX_MONTH("6month"), YEAR("year"), THREE_YEAR("3year"),
    FIVE_YEAR("5year"), TEN_YEAR("10year"), ALL_TIME("allTime");
    private final String value;
  }

  private final OFXProperties properties;
  private final WebClient ofx;

  public OFX period(Period period, String from, String to) {
    String[] args = args(from, to, properties.isInverted(from));

    return call(
        ofx.get().uri(properties.getResource().getPeriod(), period.value, args[0], args[1]),
        properties.isInverted(from),
        ERROR_PERIOD.formatted(period, from, to)
    );
  }

  public OFX range(String from, String to, LocalDate start, LocalDate end) {
    String[] args = args(from, to, properties.isInverted(from));

    return call(
        ofx.get().uri(properties.getResource().getRange(), args[0], args[1],
            start.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            end.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()),
        properties.isInverted(from),
        ERROR_RANGE.formatted(from, to, start, end)
    );
  }

  private OFX call(WebClient.RequestHeadersSpec<?> uri, boolean inverted, String error) {
    OFX response = uri.retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp
            -> Mono.error(new InternalServerErrorException(error, resp)))
        .onStatus(HttpStatusCode::is5xxServerError, resp
            -> Mono.error(new InternalServerErrorException(error, resp)))
        .bodyToMono(OFX.class)
        .block();

    if (response == null) {
      throw new InternalServerErrorException(error);
    }
    response.setInverse(inverted);

    return response;
  }

  private String[] args(String from, String to, boolean inverted) {
    if (inverted) {
      log.info("The period will be loaded inverted. Requested {}-{}  New {}-{}", from, to, to, from);
      return new String[] { to, from };
    }
    return new String[] { from, to };
  }
}
