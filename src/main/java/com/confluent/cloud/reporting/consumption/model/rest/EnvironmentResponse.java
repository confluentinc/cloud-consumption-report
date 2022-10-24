package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentResponse extends BaseResponse {
    private String id;
    private String display_name;
}
