package com.confluent.cloud.reporting.consumption.model.rest;

import com.confluent.cloud.reporting.consumption.model.KafkaClusterType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClusterConfig {
    private Integer cku;
    private KafkaClusterType kind;
    private List<String> zones;
}
