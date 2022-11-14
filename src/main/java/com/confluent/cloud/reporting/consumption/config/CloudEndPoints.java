package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloudEndPoints {
    private String environmentList;
    private String clusterList;
    private String srList;
}
