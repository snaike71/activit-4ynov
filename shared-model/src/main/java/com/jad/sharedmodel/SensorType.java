package com.jad.sharedmodel;

public enum SensorType {
    HUMIDITY("HumiditySensor", 20, 80, "%"),
    PRESSURE("PressureSensor", 800, 1200, "hPa"),
    TEMPERATURE("TemperatureSensor", -10, 40, "°C"),
    WIND_SPEED("WindSpeedSensor", 0, 150, "m/s");

    private final String sensorName;
    private final int minValue;
    private final int maxValue;
    private final String unit;

    SensorType(String sensorName, final int minValue, final int maxValue, final String unit) {
        this.sensorName = sensorName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }

    public static String getName(final SensorType sensorType) {
        return sensorType.sensorName;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public String getSensorName() {
        return this.sensorName;
    }
}

