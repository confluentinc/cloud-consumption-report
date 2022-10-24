package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MetricsRequest {
    private List<MetricsAggregation> aggregations = null;
    private MetricsFilter filter;
    private MetricsGranularity granularity;
    private List<String> intervals = null;
    private Integer limit = 1000;
    private MetricsFormat format;
}
