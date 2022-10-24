package com.confluent.cloud.reporting.consumption.integration;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.config.MetricsDefinition;
import com.confluent.cloud.reporting.consumption.config.MetricsTransformation;
import com.confluent.cloud.reporting.consumption.model.entity.Cluster;
import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetrics;
import com.confluent.cloud.reporting.consumption.model.entity.MetricsLimitId;
import com.confluent.cloud.reporting.consumption.model.rest.*;
import com.confluent.cloud.reporting.consumption.repository.MetricsLimitRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetricsList {
    AppConfig appConfig;

    RestTemplate restTemplate;

    MetricsLimitRepository metricsLimitRepository;

    public MetricsList(AppConfig appConfig, RestTemplate restTemplate, MetricsLimitRepository metricsLimitRepository) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
        this.metricsLimitRepository = metricsLimitRepository;
    }

    @RateLimiter(name = "metrics_rpm_limiter")
    public List<ClusterMetrics> getMetrics(MetricsDefinition metricsDefinition, Cluster cluster) {
        ResponseWrapper<MetricsResponse> metricsResponse = restTemplate.exchange(appConfig.getMetrics().getEndPoint(),
                HttpMethod.POST,
                new HttpEntity<>(createMetricsRequest(metricsDefinition.getName(), cluster.getId())),
                new ParameterizedTypeReference<ResponseWrapper<MetricsResponse>>() {
                }).getBody();
        return metricsResponse.getData().stream().map(er -> ClusterMetrics.builder()
                .clusterId(cluster.getId())
                .metricsName(metricsDefinition.getLimit())
                .timestamp(er.getTimestamp())
                .metric(performTransformation(er.getValue(), metricsDefinition.getTransformation()))
                .metricsLimit(getLimit(cluster, metricsDefinition.getLimit()))
                .build()
        ).collect(Collectors.toList());
    }

    private BigDecimal getLimit(Cluster cluster, String metricsName) {
        String cku_kind = ObjectUtils.firstNonNull(cluster.getCkuCount(), cluster.getKind()).toString();
        return metricsLimitRepository.findById(new MetricsLimitId(cku_kind, metricsName)).get().getMetrics_limit();
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

    private MetricsRequest createMetricsRequest(String metricName, String clusterId) {
        List<MetricsAggregation> metricsAggregationList = new ArrayList<>();
        metricsAggregationList.add(MetricsAggregation.builder().metric(metricName).build());
        List<String> intervals = new ArrayList<>();
        intervals.add("now-7d/now");
        return MetricsRequest.builder()
                .aggregations(metricsAggregationList)
                .filter(MetricsFilter.builder()
                        .op(MetricsFilterOp.EQ)
                        .field("resource.kafka.id")
                        .value(clusterId)
                        .build())
                .granularity(MetricsGranularity.PT1H)
                .intervals(intervals)
                .limit(1000)
                .format(MetricsFormat.FLAT)
                .build();
    }
}
