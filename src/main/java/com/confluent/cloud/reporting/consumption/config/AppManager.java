package com.confluent.cloud.reporting.consumption.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class AppManager {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private AppConfig appConfig;

    public void initiateShutdown(int returnCode) {
        SpringApplication.exit(appContext, () -> returnCode);
    }

    public <T> T getBean(String name) {
        return (T) appContext.getBean(name);
    }

    @PreDestroy
    public void performCleanUp() {
        try {
            FileSystemUtils.deleteRecursively(Path.of(appConfig.getReporting().getTmpDir()));
        } catch (IOException e) {
            log.info("Problem occurred during cleanup");
        } finally {
            log.info("Application clean up completed successfully");
        }
    }
}
