package com.confluent.cloud.reporting.consumption.config;

public enum MetricsTransformation {
    NONE,
    BYTES_TO_MB,
    BYTES_TO_GB,
    BYTES_TO_MB_PER_SEC,
    PERCENT
}
