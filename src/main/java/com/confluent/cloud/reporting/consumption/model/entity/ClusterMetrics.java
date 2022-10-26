package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ClusterMetricsId.class)
@Entity
public class ClusterMetrics {
    @Id
    private String clusterId;
    @Id
    private String metricsName;
    @Id
    private LocalDateTime timestamp;
    private BigDecimal metricsValue;
}
