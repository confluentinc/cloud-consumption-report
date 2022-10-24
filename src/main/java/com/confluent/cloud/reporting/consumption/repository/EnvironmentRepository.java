package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.Environment;
import org.springframework.data.repository.CrudRepository;

public interface EnvironmentRepository extends CrudRepository<Environment, String> {
}
