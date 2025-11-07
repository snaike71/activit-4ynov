package com.jad.reportgeneration;

import com.jad.sharedmodel.SensorData;

import java.text.MessageFormat;

class TextReportGenerator extends AbstractReportGenerator {

    static final ReportType REPORT_TYPE = ReportType.TEXT;

    protected TextReportGenerator() {
        super(TextReportGenerator.REPORT_TYPE);
    }

    @Override
    protected String generateHeader() {
        return MessageFormat.format("{0} Report\n==================", TextReportGenerator.REPORT_TYPE.name());
    }

    @Override
    protected String SensorDataToReport(final SensorData sensorData) {
        return MessageFormat.format("Sensor Type: {0}, Value: {1} {2}, Timestamp: {3}",
                                    sensorData.sensorType().name(),
                                    sensorData.value(),
                                    sensorData.unit(),
                                    sensorData.time().toString());

    }

    @Override
    protected void saveReport(final String report) {
        System.out.println(report);
    }
}
