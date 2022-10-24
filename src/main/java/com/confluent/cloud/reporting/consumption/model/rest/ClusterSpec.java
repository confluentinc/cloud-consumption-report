package com.confluent.cloud.reporting.consumption.model.rest;

import com.confluent.cloud.reporting.consumption.model.Availability;
import com.confluent.cloud.reporting.consumption.model.Cloud;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClusterSpec {
    private Availability availability;
    private Cloud cloud;
    private String region;
    private ClusterConfig config;
    private String display_name;
}
