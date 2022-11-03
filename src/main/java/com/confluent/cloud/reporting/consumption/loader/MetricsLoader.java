package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.integration.MetricsList;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.repository.ClusterMetricsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MetricsLoader {
    @Autowired
    MetricsList metricsList;

    @Autowired
    ClusterMetricsRepository clusterMetricsRepository;

    @Autowired
    AppConfig appConfig;

    @Transactional
    public void purgeOldMetrics() {
        log.info("Purging metrics data older than retention period {} days", appConfig.getReporting().getDataRetentionDays());
        clusterMetricsRepository.purgeWhereTimestampLessThan(LocalDateTime.now().minusDays(appConfig.getReporting().getDataRetentionDays()));
    }

    public void loadMetrics(Cluster cluster) {
        appConfig.getMetrics().getDefinitions().forEach(md -> {
            clusterMetricsRepository.saveAll(metricsList.getMetrics(md, cluster));
        });
    }
}
