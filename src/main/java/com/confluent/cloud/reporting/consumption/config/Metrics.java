package com.confluent.cloud.reporting.consumption.config;

import com.confluent.cloud.reporting.consumption.model.ClusterType;
import com.confluent.cloud.reporting.consumption.model.entity.MetricsDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Metrics {
    private String endPoint;
    private Map<ClusterType, List<MetricsDefinition>> definitions;
}
