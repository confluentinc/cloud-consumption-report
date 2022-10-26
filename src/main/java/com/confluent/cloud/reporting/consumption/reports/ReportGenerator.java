package com.confluent.cloud.reporting.consumption.reports;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.config.AppManager;
import com.confluent.cloud.reporting.consumption.loader.DataLoader;
import com.confluent.cloud.reporting.consumption.repository.ClusterRepository;
import com.confluent.cloud.reporting.consumption.repository.EnvironmentRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ReportGenerator implements ApplicationRunner {

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AppManager appManager;
    @Autowired
    private DataLoader dataLoader;
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (appConfig.getReporting().getRefreshData())
            dataLoader.loadData();
        generateReport();
    }

    public void generateReport() {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(getCompiledReport(), getReportParameters(), dataSource.getConnection());
            String outputPath = String.format("%sOrganizationReport_%s.pdf", appConfig.getReporting().getOutputDir(), new SimpleDateFormat("MM_dd_yyyy_HH_mm").format(new Date()));
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            log.info("Report Successfully generated to {}", outputPath);
        } catch (Exception ex) {
            log.error("Error in generating the report", ex);
        } finally {
            appManager.initiateShutdown(0);
        }
    }

    private Map<String, Object> getReportParameters() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Organization", appConfig.getOrganization());
        parameters.put("Logo", copyResourceToTemp(appConfig.getReporting().getConfluentLogo()));
        parameters.put("ClusterCount", clusterRepository.count());
        parameters.put("EnvironmentCount", environmentRepository.count());
        return parameters;
    }

    private String getCompiledReport() throws Exception {
        compileReport("MetricsReport");
        compileReport("ClusterReport");
        compileReport("ClusterUtilization");
        return compileReport("MasterReport");
    }

    private String copyResourceToTemp(Resource resource) throws IOException {
        String output = String.format("%s/logo.jpeg", appConfig.getReporting().getTmpDir());
        Files.copy(resource.getInputStream(), Path.of(output), StandardCopyOption.REPLACE_EXISTING);
        return output;
    }

    private String compileReport(String reportName) throws Exception {
        try {
            String reportPath = String.format("reports/%s.jrxml", reportName);
            String outputPath = String.format("%s/%s.jasper", appConfig.getReporting().getTmpDir(), reportName);
            JasperReport jasperReport = JasperCompileManager.compileReport(new ClassPathResource(reportPath).getInputStream());
            JRSaver.saveObject(jasperReport, outputPath);
            return outputPath;
        } catch (Exception e) {
            log.error("Unable to compile jasper report {}", reportName);
            throw e;
        }
    }
}
