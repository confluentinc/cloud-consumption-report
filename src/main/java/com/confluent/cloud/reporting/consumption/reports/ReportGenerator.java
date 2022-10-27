package com.confluent.cloud.reporting.consumption.reports;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.config.AppManager;
import com.confluent.cloud.reporting.consumption.config.Report;
import com.confluent.cloud.reporting.consumption.loader.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReportGenerator implements ApplicationRunner {

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private AppManager appManager;
    @Autowired
    private DataLoader dataLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            if (appConfig.getReporting().getRefreshData())
                dataLoader.loadData();
            generateReports();
        } catch (Exception ex) {
            log.error("Error in generating the report", ex);
        } finally {
            appManager.initiateShutdown(0);
        }
    }

    public void generateReports() throws Exception {
        for (Report report : appConfig.getReporting().getReportList()) {
            if (report.getIsEnabled()) {
                IReport iReport = appManager.getBean(report.getBeanName());
                iReport.generate(report.getArgs());
            }
        }
    }
}
