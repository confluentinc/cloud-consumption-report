package com.confluent.cloud.reporting.consumption.model.entity;

import com.confluent.cloud.reporting.consumption.model.ClusterType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MetricsDefinitionId implements Serializable {
    String name;
    @Enumerated(EnumType.STRING)
    ClusterType clusterType;
}
