package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetrics;
import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetricsId;
import org.springframework.data.repository.CrudRepository;

public interface ClusterMetricsRepository extends CrudRepository<ClusterMetrics, ClusterMetricsId> {
    ClusterMetrics findTopByOrderByTimestampAsc();

    ClusterMetrics findTopByOrderByTimestampDesc();
}
