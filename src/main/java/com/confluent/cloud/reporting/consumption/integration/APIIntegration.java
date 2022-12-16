package com.confluent.cloud.reporting.consumption.integration;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class APIIntegration {

    RestTemplate restTemplate;

    public APIIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "api_retry")
    public <T> ResponseEntity<T> exchangeWithRetry(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        return this.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    @RateLimiter(name = "rpm_limiter")
    private  <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }
}
