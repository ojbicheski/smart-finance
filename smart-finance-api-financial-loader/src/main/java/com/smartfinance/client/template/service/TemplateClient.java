package com.smartfinance.client.template.service;

import com.smartfinance.client.template.config.properties.TemplateProperties;
import com.smartfinance.client.template.exception.InternalServerErrorException;
import com.smartfinance.client.template.model.Template;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TemplateClient {
  private static final String ERROR = "Failed to load Template by Operator [ %s ]";

  private final TemplateProperties properties;
  private final WebClient template;

  public Template get(UUID reference) {
    return template.get()
        .uri(properties.getResource().getTemplate(), reference)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(reference), resp)))
        .onStatus(HttpStatusCode::is5xxServerError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(reference), resp)))
        .bodyToMono(Template.class)
        .block();
  }
}
