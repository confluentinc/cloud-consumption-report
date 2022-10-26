package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.repository.MetricsDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsDefinitionLoader {
    @Autowired
    AppConfig appConfig;

    @Autowired
    MetricsDefinitionRepository metricsDefinitionRepository;

    public void loadMetricsDefinitions() {
        metricsDefinitionRepository.saveAll(appConfig.getMetrics().getDefinitions());
    }
}
