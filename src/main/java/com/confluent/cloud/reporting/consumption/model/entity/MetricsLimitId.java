package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetricsLimitId implements Serializable {
    private String cku_kind;
    private String metrics_name;
}
