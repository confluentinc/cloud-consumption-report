package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    String api_version;
    String kind;
    MetaData metadata;
}
