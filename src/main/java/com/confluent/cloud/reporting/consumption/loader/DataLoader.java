package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.integration.ClusterList;
import com.confluent.cloud.reporting.consumption.integration.EnvironmentList;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.model.entity.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataLoader {
    @Autowired
    ClusterLoader clusterLoader;
    @Autowired
    EnvironmentLoader environmentLoader;
    @Autowired
    MetricsLimitLoader metricsLimitLoader;
    @Autowired
    MetricsLoader metricsLoader;
    @Autowired
    MetricsDefinitionLoader metricsDefinitionLoader;
    @Autowired
    ClusterList clusterList;
    @Autowired
    EnvironmentList environmentList;

    public void loadData() {
        metricsDefinitionLoader.loadMetricsDefinitions();
        metricsLimitLoader.loadMetrics();
        List<Environment> environments = environmentList.getEnvironments();
        environmentLoader.loadEnvironments(environments);
        environments.forEach(e -> {
            List<Cluster> clusters = clusterList.getClusters(e.getId());
            clusterLoader.loadCluster(clusters, e.getId());
            clusters.forEach(cluster -> {
                log.info("Processing Environment {}, Cluster: {}", e.getName(), cluster.getName());
                metricsLoader.loadMetrics(cluster);
            });
        });
    }
}
