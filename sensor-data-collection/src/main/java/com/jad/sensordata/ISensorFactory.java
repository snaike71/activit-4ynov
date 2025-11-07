package com.jad.sensordata;

import com.jad.sharedmodel.ISensor;
import com.jad.sharedmodel.SensorType;

public interface ISensorFactory {
    ISensor make(SensorType sensorType);
}
