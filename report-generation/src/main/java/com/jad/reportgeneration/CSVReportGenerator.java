package com.jad.reportgeneration;

import com.jad.sharedmodel.SensorData;
import com.jad.utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

class CSVReportGenerator extends AbstractReportGenerator {

    static final ReportType REPORT_TYPE = ReportType.CSV;

    protected CSVReportGenerator() {
        super(CSVReportGenerator.REPORT_TYPE);
    }

    @Override
    protected String generateHeader() {
        return MessageFormat.format("{0},{1},{2},{3}",
                                    "Sensor Type",
                                    "Value",
                                    "Unit",
                                    "Timestamp");
    }

    @Override
    protected String SensorDataToReport(final SensorData sensorData) {
        return MessageFormat.format("{0},{1},{2},{3}",
                                    sensorData.sensorType().name(),
                                    sensorData.value(),
                                    sensorData.unit(),
                                    sensorData.time().toString());
    }

    @Override
    protected void saveReport(final String report) {
        try {
            FileWriter fileWriter = new FileWriter("report-" + System.currentTimeMillis() + ".csv");
            fileWriter.write(report);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            Utils.LOGGER.logError("Error while saving report to CSV file");
            throw new RuntimeException(e);
        }
    }
}
