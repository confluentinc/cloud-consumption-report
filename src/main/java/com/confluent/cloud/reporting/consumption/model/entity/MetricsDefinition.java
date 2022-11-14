package com.confluent.cloud.reporting.consumption.model.entity;

import com.confluent.cloud.reporting.consumption.config.MetricsTransformation;
import com.confluent.cloud.reporting.consumption.model.ClusterType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MetricsDefinitionId.class)
@Builder
@Entity
public class MetricsDefinition {
    @Id
    String name;
    @Id
    @Enumerated(EnumType.STRING)
    ClusterType clusterType;
    @Enumerated(EnumType.STRING)
    MetricsTransformation transformation = MetricsTransformation.NONE;
    String metricsLimit;
    String category;
}
