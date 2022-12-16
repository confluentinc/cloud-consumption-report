package com.confluent.cloud.reporting.consumption.integration;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.config.MetricsTransformation;
import com.confluent.cloud.reporting.consumption.model.ClusterType;
import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetrics;
import com.confluent.cloud.reporting.consumption.model.entity.MetricsDefinition;
import com.confluent.cloud.reporting.consumption.model.rest.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetricsList {
    AppConfig appConfig;

    APIIntegration apiIntegration;

    public MetricsList(AppConfig appConfig, APIIntegration apiIntegration) {
        this.appConfig = appConfig;
        this.apiIntegration = apiIntegration;
    }

    public List<ClusterMetrics> getMetrics(MetricsDefinition metricsDefinition, String clusterId, ClusterType clusterType) {
        ResponseWrapper<MetricsResponse> metricsResponse = apiIntegration.exchangeWithRetry(appConfig.getMetrics().getEndPoint(),
                HttpMethod.POST,
                new HttpEntity<>(createMetricsRequest(metricsDefinition.getName(), clusterId, clusterType)),
                new ParameterizedTypeReference<ResponseWrapper<MetricsResponse>>() {
                }).getBody();
        if (CollectionUtils.isEmpty(metricsResponse.getData())) return new ArrayList<>();
        return metricsResponse.getData().stream().map(er -> ClusterMetrics.builder()
                .clusterId(clusterId)
                .metricsName(metricsDefinition.getMetricsLimit())
                .timestamp(er.getTimestamp())
                .metricsValue(performTransformation(er.getValue(), metricsDefinition.getTransformation()))
                .build()
        ).collect(Collectors.toList());
    }

    private BigDecimal performTransformation(Double value, MetricsTransformation transformation) {
        switch (transformation) {
            case PERCENT:
                return BigDecimal.valueOf(value * 100);
            case BYTES_TO_GB:
                return BigDecimal.valueOf(value / 1024 / 1024 / 1024);
            case BYTES_TO_MB:
                return BigDecimal.valueOf(value / 1024 / 1024);
            case BYTES_TO_MB_PER_SEC:
                return BigDecimal.valueOf(value / 1024 / 1024 / 60 / 60);
            default:
                return BigDecimal.valueOf(value);
        }
    }

    private MetricsRequest createMetricsRequest(String metricName, String clusterId, ClusterType clusterType) {
        List<MetricsAggregation> metricsAggregationList = new ArrayList<>();
        metricsAggregationList.add(MetricsAggregation.builder().metric(metricName).build());
        List<String> intervals = new ArrayList<>();
        intervals.add("now-7d/now");
        return MetricsRequest.builder()
                .aggregations(metricsAggregationList)
                .filter(MetricsFilter.builder()
                        .op(MetricsFilterOp.EQ)
                        .field(String.format("resource.%s.id", clusterType.name()))
                        .value(clusterId)
                        .build())
                .granularity(MetricsGranularity.PT1H)
                .intervals(intervals)
                .limit(1000)
                .format(MetricsFormat.FLAT)
                .build();
    }
}
