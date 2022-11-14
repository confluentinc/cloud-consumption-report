package com.confluent.cloud.reporting.consumption.model.entity;

import com.confluent.cloud.reporting.consumption.model.SRClusterType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SRCluster {
    @Id
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private SRClusterType kind;
    private String environmentId;
}
