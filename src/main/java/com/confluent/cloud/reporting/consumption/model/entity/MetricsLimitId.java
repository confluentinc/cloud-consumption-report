package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MetricsLimitId implements Serializable {
    private String cku_kind;
    private String metrics_name;
}
