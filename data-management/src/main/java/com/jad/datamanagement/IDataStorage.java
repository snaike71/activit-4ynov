package com.jad.datamanagement;

import com.jad.sharedmodel.SensorData;
import com.jad.sharedmodel.SensorType;

import java.util.List;

public interface IDataStorage {
    List<SensorData> getAllStoredData();

    List<SensorData> getAllDataBySensorType(SensorType sensorType);
}
