package com.confluent.cloud.reporting.consumption.integration;

import au.com.bytecode.opencsv.CSVReader;
import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.model.entity.SRLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SRLimitList {

    @Autowired
    AppConfig appConfig;

    public List<SRLimit> getMetricsLimits() {
        List<SRLimit> srLimits = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(appConfig.getReporting().getSrLimitsFileLocation().getInputStream()))) {
            String[] allColumnNames;
            String[] values;
            allColumnNames = csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                for (int index = 1; index < values.length; index++) {
                    srLimits.add(SRLimit.builder()
                            .kind(values[0])
                            .metrics_limit((values[index] == null || values[index].isBlank()) ? null : new BigDecimal(values[index]))
                            .metrics_name(allColumnNames[index])
                            .build());
                }
            }
        } catch (Exception ex) {
            log.error("Error in reading schema registry limits", ex);
        }
        return srLimits;
    }
}
