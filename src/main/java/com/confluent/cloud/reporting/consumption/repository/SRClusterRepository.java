package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.SRCluster;
import org.springframework.data.repository.CrudRepository;

public interface SRClusterRepository extends CrudRepository<SRCluster, String> {
    int deleteAllByEnvironmentId(String environmentId);
}
