package com.confluent.cloud.reporting.consumption.reports;

import java.util.Map;

public interface IReport {
    void generate(Map<String, String> args) throws Exception;
}
