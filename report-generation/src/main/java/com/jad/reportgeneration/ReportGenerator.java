package com.jad.reportgeneration;

import com.jad.sharedmodel.SensorData;

import java.util.List;

public class ReportGenerator implements IReportGenerator {
    @Override
    public void generate(final ReportType reportType, List<SensorData> data) {
        switch (reportType) {
            case TEXT -> new TextReportGenerator().generateReport(data);
            case CSV -> new CSVReportGenerator().generateReport(data);
        }
    }
}
