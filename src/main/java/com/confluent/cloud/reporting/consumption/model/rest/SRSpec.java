package com.confluent.cloud.reporting.consumption.model.rest;

import com.confluent.cloud.reporting.consumption.model.SRClusterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SRSpec {
    @JsonProperty("package")
    private SRClusterType type;
}
