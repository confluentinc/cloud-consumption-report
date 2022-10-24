package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Metrics {
    private String endPoint;
    private List<MetricsDefinition> definitions;
}
