package com.jad.datamanagement;

import com.jad.sharedmodel.ISensor;
import com.jad.sharedmodel.SensorData;

import java.util.List;

public interface IDataManager extends IDataProcessor {
    void addDataCollector(ISensor sensor);

    void collectAndStoreData();

    List<SensorData> getAllData();
}

