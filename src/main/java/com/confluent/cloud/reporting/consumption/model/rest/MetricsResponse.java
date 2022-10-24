package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MetricsResponse {
    private LocalDateTime timestamp;
    private Double value;
}
