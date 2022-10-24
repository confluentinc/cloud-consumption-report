package com.confluent.cloud.reporting.consumption.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "confluent.cloud")
public class AppConfig {
    String organization;
    String apiKey;
    String apiSecret;
    CloudEndPoints cloudEndPoints;
    Metrics metrics;
    Reporting reporting;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthentication(getApiKey(), getApiSecret())
                .build();
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Path.of(reporting.getTmpDir()));
    }
}
