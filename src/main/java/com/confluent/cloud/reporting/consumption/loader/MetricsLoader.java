package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.integration.MetricsList;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.repository.ClusterMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsLoader {
    @Autowired
    MetricsList metricsList;

    @Autowired
    ClusterMetricsRepository clusterMetricsRepository;

    @Autowired
    AppConfig appConfig;

    public void loadMetrics(Cluster cluster) {
        appConfig.getMetrics().getDefinitions().forEach(md -> {
            clusterMetricsRepository.saveAll(metricsList.getMetrics(md, cluster));
        });
    }
}
