package com.jad.datamanagement;

import com.jad.sharedmodel.SensorData;

import java.time.Duration;
import java.util.Optional;

class DataValidator {
    private final static double MAX_PERCENTAGE_DIFFERENCE_IN_ONE_MINUTE = 0.01;

    public final boolean validate(DataProcessor dataProcessor, SensorData sensorData) {
        return this.validateMinMaxValues(sensorData)
                && this.validateTimeDifference(dataProcessor, sensorData);
    }

    private boolean validateMinMaxValues(SensorData sensorData) {
        return (sensorData.value() >= sensorData.getMinValue())
                && (sensorData.value() <= sensorData.getMaxValue());
    }

    private boolean validateTimeDifference(DataProcessor dataProcessor, SensorData sensorData) {
        Optional<SensorData> lastSensorData = dataProcessor.calculateLastSensorDataBySensorType(
                sensorData.sensorType());
        if (lastSensorData.isEmpty()) return true;
        double maxDifference = (sensorData.getMaxValue() - sensorData.getMinValue())
                * DataValidator.MAX_PERCENTAGE_DIFFERENCE_IN_ONE_MINUTE;
        double minutesDifference = DataValidator.getTimeDifference(sensorData, lastSensorData.get());
        if (minutesDifference == 0) minutesDifference = 1;
        return maxDifference >= (DataValidator.getValueDifference(sensorData, lastSensorData.get())
                / minutesDifference);
    }

    private static long getTimeDifference(final SensorData first, final SensorData second) {
        return Duration.between(first.time(), second.time()).toMinutes();
    }

    private static int getValueDifference(final SensorData first, final SensorData second) {
        return Math.abs(first.value() - second.value());
    }
}
