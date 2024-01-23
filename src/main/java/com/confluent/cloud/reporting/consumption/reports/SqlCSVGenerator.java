package com.confluent.cloud.reporting.consumption.reports;

import au.com.bytecode.opencsv.CSVWriter;
import com.confluent.cloud.reporting.consumption.config.AppConfig;
import com.confluent.cloud.reporting.consumption.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import static com.confluent.cloud.reporting.consumption.util.Constants.REPORT_SQL_PATH;

@Component
@Slf4j
public class SqlCSVGenerator implements IReport {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void generate(Map<String, String> args) throws Exception {
        for (String sqlFile : args.get(ReportArgs.sqlFileList.name()).split(",")) {
            try {
                log.info("Generating CSV for {}", sqlFile);
                String outputPath = String.format("%s%s_%s.csv", appConfig.getReporting().getOutputDir(), sqlFile, CommonUtils.formatFileDate(LocalDateTime.now()));
                generateCSV(sqlFile, outputPath);
                log.info("{} CSV successfully generated to {}", sqlFile, outputPath);
            } catch (Exception ex) {
                throw new Exception(String.format("Error in generating %s CSV", sqlFile), ex);
            }
        }
    }

    private void generateCSV(String sqlFile, String outputPath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            jdbcTemplate.query(getQuery(sqlFile), (ResultSetExtractor<Void>) rs -> {
                try {
                    csvWriter.writeAll(rs, true);
                } catch (IOException e) {
                    throw new RuntimeException("Error in reading the recordset", e);
                }
                return null;
            });
        }
    }

    private String getQuery(String sqlFile) throws IOException {
        String sqlFilePath = String.format("%s%s.sql", REPORT_SQL_PATH, sqlFile);
        return IOUtils.toString(new ClassPathResource(sqlFilePath).getInputStream(), StandardCharsets.UTF_8);
    }

    private enum ReportArgs {
        sqlFileList
    }
}
