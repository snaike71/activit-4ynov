package com.jad.sensordata;

import com.jad.sharedmodel.ISensor;
import com.jad.sharedmodel.SensorData;
import com.jad.sharedmodel.SensorType;
import com.jad.utils.Utils;

import java.util.Random;

abstract class AbstractSensor implements ISensor {
    static final int START_VALUE = 99999;
    private static final double MAX_PERCENTAGE_DIFFERENCE = 0.01;
    private final Random random = new Random();
    private final SensorType sensorType;
    private double lastValue = AbstractSensor.START_VALUE;

    AbstractSensor(final SensorType sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public final SensorType getSensorType() {
        return this.sensorType;
    }

    @Override
    public final SensorData getSensorData() {
        return new SensorData(this.sensorType, this.getUnit(), (int) this.getValue());
    }

    public final String getUnit() {
        return this.sensorType.getUnit();
    }

    public final double getValue() {
        Utils.LOGGER.logInfo("Collecting data from " + this.sensorType.getSensorName() + " sensor");
        if (this.lastValue == AbstractSensor.START_VALUE) {
            this.lastValue = this.sensorType.getMinValue() + (this.sensorType.getMaxValue() - this.sensorType.getMinValue()) * this.random.nextDouble();
        } else {
            double maxDifference = (this.sensorType.getMaxValue() - this.sensorType.getMinValue())
                    * AbstractSensor.MAX_PERCENTAGE_DIFFERENCE;
            this.lastValue = Math.max(this.sensorType.getMinValue(),
                                      Math.min(this.sensorType.getMaxValue(),
                                               this.lastValue + (maxDifference * this.random.nextDouble() * (
                                                       this.random.nextBoolean() ? 1 : -1))));
        }
        return this.lastValue;
    }
}
