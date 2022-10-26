package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.MetricsDefinition;
import org.springframework.data.repository.CrudRepository;

public interface MetricsDefinitionRepository extends CrudRepository<MetricsDefinition, String> {
}
