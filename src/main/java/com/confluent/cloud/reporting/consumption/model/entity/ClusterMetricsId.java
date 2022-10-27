package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ClusterMetricsId implements Serializable {
    private String clusterId;
    private String metricsName;
    private LocalDateTime timestamp;
}
