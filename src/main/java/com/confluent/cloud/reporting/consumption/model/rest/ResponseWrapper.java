package com.confluent.cloud.reporting.consumption.model.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseWrapper<T> extends BaseResponse {
    private List<T> data;
}
