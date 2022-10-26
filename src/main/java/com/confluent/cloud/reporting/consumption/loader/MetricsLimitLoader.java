package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.integration.MetricsLimitList;
import com.confluent.cloud.reporting.consumption.repository.MetricsLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsLimitLoader {
    @Autowired
    MetricsLimitList metricsLimitList;

    @Autowired
    MetricsLimitRepository metricsLimitRepository;

    public void loadMetrics() {
        metricsLimitRepository.saveAll(metricsLimitList.getMetricsLimits());
    }
}
