package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import org.springframework.data.repository.CrudRepository;

public interface ClusterRepository extends CrudRepository<Cluster, String> {
    int deleteAllByEnvironmentId(String environmentId);
}
