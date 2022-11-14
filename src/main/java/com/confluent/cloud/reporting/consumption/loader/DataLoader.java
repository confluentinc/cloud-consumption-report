package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.integration.ClusterList;
import com.confluent.cloud.reporting.consumption.integration.EnvironmentList;
import com.confluent.cloud.reporting.consumption.integration.SRClusterList;
import com.confluent.cloud.reporting.consumption.model.ClusterType;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.model.entity.Environment;
import com.confluent.cloud.reporting.consumption.model.entity.SRCluster;
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
    SRLimitLoader srLimitLoader;
    @Autowired
    SRClusterLoader srClusterLoader;
    @Autowired
    SRClusterList srClusterList;
    @Autowired
    ClusterList clusterList;
    @Autowired
    EnvironmentList environmentList;

    public void loadData() {
        metricsLoader.purgeOldMetrics();
        loadConfigurationData();
        List<Environment> environments = environmentList.getEnvironments();
        environmentLoader.loadEnvironments(environments);
        environments.forEach(e -> {
            loadKafkaClusterData(e);
            loadSRClusterData(e);
        });
    }

    public void loadConfigurationData(){
        metricsDefinitionLoader.loadMetricsDefinitions();
        metricsLimitLoader.loadMetrics();
        srLimitLoader.loadMetrics();
    }

    public void loadKafkaClusterData(Environment e){
        List<Cluster> clusters = clusterList.getClusters(e.getId());
        clusterLoader.loadCluster(clusters, e.getId());
        clusters.forEach(cluster -> {
            log.info("Processing Environment {}, Cluster: {}", e.getName(), cluster.getName());
            metricsLoader.loadMetrics(cluster.getId(), ClusterType.kafka);
        });
    }

    public void loadSRClusterData(Environment e){
        List<SRCluster> srClusters = srClusterList.getClusters(e.getId());
        srClusterLoader.loadCluster(srClusters, e.getId());
        srClusters.forEach(cluster -> {
            log.info("Processing Environment {}, Schema Registry Cluster: {}", e.getName(), cluster.getId());
            metricsLoader.loadMetrics(cluster.getId(), ClusterType.schema_registry);
        });
    }
}
