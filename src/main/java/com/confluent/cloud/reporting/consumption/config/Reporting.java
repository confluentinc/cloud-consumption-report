package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
public class Reporting {
    private String outputDir;
    private String tmpDir;
    private Resource limitsFileLocation;
    private Resource confluentLogo;
}
