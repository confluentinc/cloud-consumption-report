package com.confluent.cloud.reporting.consumption.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class OrgMetrics {
    private Integer clusterCount = 0;
    private Integer environmentCount = 0;
}
