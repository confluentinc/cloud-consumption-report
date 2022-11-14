package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.model.entity.SRCluster;
import com.confluent.cloud.reporting.consumption.repository.SRClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SRClusterLoader {
    @Autowired
    SRClusterRepository srClusterRepository;

    public void loadCluster(List<SRCluster> clusters, String environmentId) {
        srClusterRepository.deleteAllByEnvironmentId(environmentId);
        srClusterRepository.saveAll(clusters);
    }
}
