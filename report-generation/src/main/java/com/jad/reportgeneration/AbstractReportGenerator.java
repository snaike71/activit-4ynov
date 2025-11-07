package com.jad.reportgeneration;

import com.jad.sharedmodel.SensorData;
import com.jad.utils.Utils;

import java.text.MessageFormat;
import java.util.List;

abstract class AbstractReportGenerator {
    private final ReportType reportType;

    protected AbstractReportGenerator(final ReportType reportType) {
        this.reportType = reportType;
    }

    public void generateReport(List<SensorData> data) {
        Utils.LOGGER.logInfo(MessageFormat.format("Generating {0} report for {1} sensor data",
                                                  this.reportType.getName(),
                                                  data.size()));
        StringBuilder report = new StringBuilder();
        report.append(this.generateHeader()).append("\n");
        for (SensorData sensorData : data) {
            report.append(this.SensorDataToReport(sensorData)).append("\n");
        }
        this.saveReport(report.toString());
    }

    protected abstract String generateHeader();

    protected abstract String SensorDataToReport(SensorData sensorData);

    protected abstract void saveReport(final String report);
}
