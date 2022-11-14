package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SRLimitId implements Serializable {
    private String kind;
    private String metrics_name;
}
