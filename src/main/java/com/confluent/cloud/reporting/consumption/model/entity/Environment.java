package com.confluent.cloud.reporting.consumption.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Environment {
    @Id
    private String id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
