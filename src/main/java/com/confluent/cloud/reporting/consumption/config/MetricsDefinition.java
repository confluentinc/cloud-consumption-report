package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricsDefinition {
    String name;
    MetricsTransformation transformation = MetricsTransformation.NONE;
    String limit;
}
