package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MetaData {
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
