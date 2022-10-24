package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.MetricsLimit;
import com.confluent.cloud.reporting.consumption.model.entity.MetricsLimitId;
import org.springframework.data.repository.CrudRepository;

public interface MetricsLimitRepository extends CrudRepository<MetricsLimit, MetricsLimitId> {
}
