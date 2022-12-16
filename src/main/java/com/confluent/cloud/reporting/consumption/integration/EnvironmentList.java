package com.confluent.cloud.reporting.consumption.integration;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.model.entity.Environment;
import com.confluent.cloud.reporting.consumption.model.rest.EnvironmentResponse;
import com.confluent.cloud.reporting.consumption.model.rest.ResponseWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnvironmentList {
    AppConfig appConfig;

    APIIntegration apiIntegration;

    public EnvironmentList(AppConfig appConfig, APIIntegration apiIntegration) {
        this.appConfig = appConfig;
        this.apiIntegration = apiIntegration;
    }

    public List<Environment> getEnvironments() {
        ResponseWrapper<EnvironmentResponse> environmentResponses = apiIntegration.exchangeWithRetry(appConfig.getCloudEndPoints().getEnvironmentList(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseWrapper<EnvironmentResponse>>() {
                }).getBody();
        if (CollectionUtils.isEmpty(environmentResponses.getData())) return new ArrayList<>();
        return environmentResponses.getData().stream().map(er -> Environment.builder()
                .id(er.getId())
                .name(er.getDisplay_name())
                .createdDate(er.getMetadata().getCreated_at())
                .updatedDate(er.getMetadata().getUpdated_at())
                .build()
        ).collect(Collectors.toList());
    }
}
