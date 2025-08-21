package com.smartfinance.client.ofx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.smartfinance.json.LocalDateFromEpochDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OFX {
  @JsonProperty("CurrentInterbankRate")
  private double currentInterbankRate;
  @JsonProperty("CurrentInverseInterbankRate")
  private double currentInverseInterbankRate;
  @JsonProperty("Average")
  private double average;
  @JsonProperty
  @Builder.Default
  private boolean inverse = false;

  @JsonProperty("HistoricalPoints")
  private List<HistoricalPoint> historical;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class HistoricalPoint {
    @JsonDeserialize(using = LocalDateFromEpochDeserializer.class)
    @JsonProperty("PointInTime")
    private LocalDate pointInTime;
    @JsonProperty("InterbankRate")
    private double interbankRate;
    @JsonProperty("InverseInterbankRate")
    private double inverseInterbankRate;
    @JsonProperty
    @Builder.Default
    private boolean inverse = false;

    HistoricalPoint invert() {
      inverse = true;
      return this;
    }
    public double rate() {
      return inverse ? inverseInterbankRate : interbankRate;
    }
  }

  public Stream<HistoricalPoint> stream() {
    return inverse
        ? historical.stream().map(HistoricalPoint::invert)
        : historical.stream();
  }
}
