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
        appConfig.getMetrics().getDefinitions().forEach((ct, mds) -> {
            mds.forEach(md -> {
                md.setClusterType(ct);
                metricsDefinitionRepository.save(md);
            });
        });
    }
}
