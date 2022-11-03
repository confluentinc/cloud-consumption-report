package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetrics;
import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetricsId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ClusterMetricsRepository extends CrudRepository<ClusterMetrics, ClusterMetricsId> {
    ClusterMetrics findTopByOrderByTimestampAsc();

    ClusterMetrics findTopByOrderByTimestampDesc();

    @Modifying
    @Query("DELETE FROM ClusterMetrics WHERE timestamp<?1")
    void purgeWhereTimestampLessThan(LocalDateTime localDateTime);
}
