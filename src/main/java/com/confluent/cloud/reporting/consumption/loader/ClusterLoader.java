package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.repository.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClusterLoader {
    @Autowired
    ClusterRepository clusterRepository;

    public void loadCluster(List<Cluster> clusters, String environmentId) {
        clusterRepository.deleteAllByEnvironmentId(environmentId);
        clusterRepository.saveAll(clusters);
    }
}
