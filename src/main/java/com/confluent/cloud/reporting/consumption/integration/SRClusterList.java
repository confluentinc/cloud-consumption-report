package com.confluent.cloud.reporting.consumption.integration;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.model.entity.SRCluster;
import com.confluent.cloud.reporting.consumption.model.rest.ResponseWrapper;
import com.confluent.cloud.reporting.consumption.model.rest.SRResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SRClusterList {
    AppConfig appConfig;

    APIIntegration apiIntegration;

    public SRClusterList(AppConfig appConfig, APIIntegration apiIntegration) {
        this.appConfig = appConfig;
        this.apiIntegration = apiIntegration;
    }

    public List<SRCluster> getClusters(String environmentId) {
        ResponseWrapper<SRResponse> srResponses = apiIntegration.exchangeWithRetry(String.format(appConfig.getCloudEndPoints().getSrList(), environmentId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseWrapper<SRResponse>>() {
                }).getBody();

        if (CollectionUtils.isEmpty(srResponses.getData())) return new ArrayList<>();
        return srResponses.getData().stream().map(sr -> SRCluster.builder()
                .id(sr.getId())
                .createdDate(sr.getMetadata().getCreated_at())
                .updatedDate(sr.getMetadata().getUpdated_at())
                .kind(sr.getSpec().getType())
                .environmentId(environmentId)
                .build()
        ).collect(Collectors.toList());
    }
}
