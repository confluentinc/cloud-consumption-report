package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Report {
    private String beanName;
    private Boolean isEnabled;
    private Map<String, String> args;
}
