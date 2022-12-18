package com.confluent.cloud.reporting.consumption.reports;

import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.model.entity.ClusterMetrics;
import com.confluent.cloud.reporting.consumption.repository.ClusterMetricsRepository;
import com.confluent.cloud.reporting.consumption.repository.ClusterRepository;
import com.confluent.cloud.reporting.consumption.repository.EnvironmentRepository;
import com.confluent.cloud.reporting.consumption.util.CommonUtils;
import com.confluent.cloud.reporting.consumption.util.Constants;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JasperPDFGenerator implements IReport {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private ClusterMetricsRepository clusterMetricsRepository;

    @Override
    public void generate(Map<String, String> args) throws Exception {
        try {
            compileReports(args);
            generateReports(args);
        } catch (Exception ex) {
            throw new Exception("Error in generating Organization Report PDF", ex);
        }
    }

    public void generateReports(Map<String, String> args) throws Exception {
        for (String mainReportName : args.get(ReportArgs.mainReportList.name()).split(",")) {
            try {
                log.info("Generating PDF for {}", mainReportName);
                JasperPrint jasperPrint = JasperFillManager.fillReport(getJasperFilePath(mainReportName), getReportParameters()
                        , dataSource.getConnection());
                String outputPath = String.format("%s%s_%s.pdf", appConfig.getReporting().getOutputDir(), mainReportName
                        , CommonUtils.formatFileDate(LocalDateTime.now()));
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                log.info("{} PDF successfully generated to {}", mainReportName, outputPath);
            } catch (Exception ex) {
                throw new Exception(String.format("Error in generating %s PDF", mainReportName), ex);
            }
        }
    }

    private Map<String, Object> getReportParameters() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Organization", appConfig.getOrganization());
        parameters.put("Logo", copyResourceToTemp(appConfig.getReporting().getConfluentLogo()));
        parameters.put("ClusterCount", clusterRepository.count());
        parameters.put("EnvironmentCount", environmentRepository.count());
        parameters.put("minDate", getFormattedDate(clusterMetricsRepository.findTopByOrderByTimestampAsc()));
        parameters.put("maxDate", getFormattedDate(clusterMetricsRepository.findTopByOrderByTimestampDesc()));
        return parameters;
    }

    public String getFormattedDate(ClusterMetrics clusterMetrics) {
        LocalDateTime dateTime = null;
        if (clusterMetrics == null) {
            dateTime = LocalDateTime.now();
        } else {
            dateTime = clusterMetrics.getTimestamp();
        }
        return CommonUtils.formatDate(dateTime);
    }

    private void compileReports(Map<String, String> args) throws Exception {
        for (String jasperReportName : args.get(ReportArgs.jasperReportList.name()).split(",")) {
            compileReport(jasperReportName);
        }
    }

    private String copyResourceToTemp(Resource resource) throws IOException {
        String output = String.format("%s/logo.jpeg", appConfig.getReporting().getTmpDir());
        Files.copy(resource.getInputStream(), Path.of(output), StandardCopyOption.REPLACE_EXISTING);
        return output;
    }

    private void compileReport(String reportName) throws Exception {
        try {
            String reportPath = String.format("%s%s.jrxml", Constants.REPORT_BASE_PATH, reportName);
            JasperReport jasperReport = JasperCompileManager.compileReport(new ClassPathResource(reportPath).getInputStream());
            JRSaver.saveObject(jasperReport, getJasperFilePath(reportName));
        } catch (Exception e) {
            log.error("Unable to compile jasper report {}", reportName);
            throw e;
        }
    }

    private String getJasperFilePath(String reportName) {
        return String.format("%s/%s.jasper", appConfig.getReporting().getTmpDir(), reportName);
    }

    private enum ReportArgs {
        jasperReportList, mainReportList
    }
}
