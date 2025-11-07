package com.jad.sharedmodel;

import java.time.LocalDateTime;

public record SensorData(LocalDateTime time, SensorType sensorType, String unit, int value) {
    public SensorData(SensorType sensorType, String unit, int value) {
        this(LocalDateTime.now(), sensorType, unit, value);
    }

    public int getMinValue() {
        return this.sensorType.getMinValue();
    }

    public int getMaxValue() {
        return this.sensorType.getMaxValue();
    }

    public String getSensorName() {
        return this.sensorType.getSensorName();
    }
}

