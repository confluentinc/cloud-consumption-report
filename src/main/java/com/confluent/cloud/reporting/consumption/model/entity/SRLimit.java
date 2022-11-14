package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@IdClass(SRLimitId.class)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SRLimit {
    @Id
    private String kind;
    @Id
    private String metrics_name;
    @Column(nullable = true)
    private BigDecimal metrics_limit;
}
