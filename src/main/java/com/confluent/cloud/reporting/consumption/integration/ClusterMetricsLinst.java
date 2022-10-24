package com.confluent.cloud.reporting.consumption.integration;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.model.rest.ClusterResponse;
import com.confluent.cloud.reporting.consumption.model.rest.ResponseWrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClusterMetricsLinst {
    AppConfig appConfig;

    RestTemplate restTemplate;

    public ClusterMetricsLinst(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    public List<Cluster> getClusters(String environmentId) {
        ResponseWrapper<ClusterResponse> clusterResponses = restTemplate.exchange(String.format(appConfig.getCloudEndPoints().getClusterList(), environmentId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseWrapper<ClusterResponse>>() {
                }).getBody();
        return clusterResponses.getData().stream().map(cr -> Cluster.builder()
                .id(cr.getId())
                .name(cr.getSpec().getDisplay_name())
                .createdDate(cr.getMetadata().getCreated_at())
                .updatedDate(cr.getMetadata().getUpdated_at())
                .availability(cr.getSpec().getAvailability())
                .ckuCount(cr.getSpec().getConfig().getCku())
                .region(cr.getSpec().getRegion())
                .cloud(cr.getSpec().getCloud())
                .kind(cr.getSpec().getConfig().getKind())
                .environmentId(environmentId)
                .build()
        ).collect(Collectors.toList());
    }
}
