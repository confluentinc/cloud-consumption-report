package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.integration.SRLimitList;
import com.confluent.cloud.reporting.consumption.repository.SRLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SRLimitLoader {
    @Autowired
    SRLimitList srLimitList;

    @Autowired
    SRLimitRepository srLimitRepository;

    public void loadMetrics() {
        srLimitRepository.saveAll(srLimitList.getMetricsLimits());
    }
}
