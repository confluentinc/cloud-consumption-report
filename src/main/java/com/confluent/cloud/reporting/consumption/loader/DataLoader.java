package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.integration.ClusterList;
import com.confluent.cloud.reporting.consumption.integration.EnvironmentList;
import com.confluent.cloud.reporting.consumption.model.OrgMetrics;
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
    ClusterList clusterList;

    @Autowired
    EnvironmentList environmentList;

    @Autowired
    OrgMetrics orgMetrics;

    public void loadData() {
        metricsLimitLoader.LoadMetrics();
        List<Environment> environments = environmentList.getEnvironments();
        environmentLoader.loadEnvironments(environments);
        orgMetrics.setEnvironmentCount(environments.size());
        environments.forEach(e -> {
            log.info("Processing environment: {}", e.getName());
            List<Cluster> clusters = clusterList.getClusters(e.getId());
            clusterLoader.loadCluster(clusters);
            orgMetrics.setClusterCount(orgMetrics.getClusterCount() + clusters.size());
            clusters.forEach(cluster -> metricsLoader.loadMetrics(cluster));
        });
    }
}
