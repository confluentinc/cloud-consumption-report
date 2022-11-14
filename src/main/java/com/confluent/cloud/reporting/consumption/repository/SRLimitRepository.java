package com.confluent.cloud.reporting.consumption.repository;

import com.confluent.cloud.reporting.consumption.model.entity.SRLimit;
import com.confluent.cloud.reporting.consumption.model.entity.SRLimitId;
import org.springframework.data.repository.CrudRepository;

public interface SRLimitRepository extends CrudRepository<SRLimit, SRLimitId> {
}
