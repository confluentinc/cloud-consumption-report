package com.confluent.cloud.reporting.consumption.model.entity;

import com.confluent.cloud.reporting.consumption.model.Availability;
import com.confluent.cloud.reporting.consumption.model.Cloud;
import com.confluent.cloud.reporting.consumption.model.ClusterType;
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
public class Cluster {
    @Id
    private String id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private ClusterType kind;
    private Integer ckuCount;
    @Enumerated(EnumType.STRING)
    private Cloud cloud;
    @Enumerated(EnumType.STRING)
    private Availability availability;
    private String region;
    private String environmentId;
}
