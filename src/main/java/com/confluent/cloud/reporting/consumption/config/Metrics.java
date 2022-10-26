package com.confluent.cloud.reporting.consumption.config;

import com.confluent.cloud.reporting.consumption.model.entity.MetricsDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Metrics {
    private String endPoint;
    private List<MetricsDefinition> definitions;
}
