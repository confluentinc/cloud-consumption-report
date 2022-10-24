package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MetricsFilter {
    private String field;
    private MetricsFilterOp op;
    private String value;
}
