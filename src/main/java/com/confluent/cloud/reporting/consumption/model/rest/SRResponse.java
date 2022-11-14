package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SRResponse extends BaseResponse {
    private String id;
    private SRSpec spec;

}
