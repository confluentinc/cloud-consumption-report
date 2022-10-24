package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClusterResponse extends BaseResponse {
    private String id;
    private ClusterSpec spec;

}
