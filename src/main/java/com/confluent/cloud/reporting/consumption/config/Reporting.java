package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
public class Reporting {
    private Boolean refreshData = true;
    private Integer dataRetentionDays;
    private String outputDir;
    private String tmpDir;
    private Resource limitsFileLocation;
    private Resource srLimitsFileLocation;
    private Resource confluentLogo;
    private List<Report> reportList;
}
