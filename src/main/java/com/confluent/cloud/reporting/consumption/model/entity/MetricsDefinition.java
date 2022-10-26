package com.confluent.cloud.reporting.consumption.model.entity;

import com.confluent.cloud.reporting.consumption.config.MetricsTransformation;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MetricsDefinition {
    @Id
    String name;
    @Enumerated(EnumType.STRING)
    MetricsTransformation transformation = MetricsTransformation.NONE;
    String metricsLimit;
    String category;
}
