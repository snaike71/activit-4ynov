package com.jad.datamanagement;

import com.jad.sharedmodel.SensorData;
import com.jad.sharedmodel.SensorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataStorage implements IDataStorage {
    private final List<SensorData> storage = new ArrayList<>();

    public final void storeData(final SensorData sensorData) {
        this.storage.add(sensorData);
    }

    @Override
    public final List<SensorData> getAllStoredData() {
        return Collections.unmodifiableList(this.storage);
    }

    @Override
    public final List<SensorData> getAllDataBySensorType(final SensorType sensorType) {
        List<SensorData> result = new ArrayList<>();
        for (SensorData sensorData : this.storage) {
            if (sensorData.sensorType().equals(sensorType)) {
                result.add(sensorData);
            }
        }
        return Collections.unmodifiableList(result);
    }
}
