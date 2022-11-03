package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClusterRepository extends CrudRepository<Cluster, String> {
    List<Cluster> findAllByEnvironmentId(String environmentId);
}
