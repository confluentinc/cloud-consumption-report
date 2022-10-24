package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClusterMetricsId implements Serializable {
    private String clusterId;
    private String metricsName;
    private LocalDateTime timestamp;
}
