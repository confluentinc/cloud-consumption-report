package com.confluent.cloud.reporting.consumption.loader;

import com.confluent.cloud.reporting.consumption.model.entity.Environment;
import com.confluent.cloud.reporting.consumption.repository.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnvironmentLoader {

    @Autowired
    EnvironmentRepository environmentRepository;

    public void loadEnvironments(List<Environment> environments) {
        environmentRepository.deleteAll();
        environmentRepository.saveAll(environments);
    }
}
