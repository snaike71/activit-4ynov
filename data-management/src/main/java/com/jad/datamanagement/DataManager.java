package com.jad.datamanagement;

import com.jad.sharedmodel.ISensor;
import com.jad.sharedmodel.SensorData;
import com.jad.sharedmodel.SensorType;
import com.jad.utils.Utils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataManager implements IDataManager {
    private final List<DataCollector> dataCollectors = new ArrayList<>();
    private final DataStorage dataStorage = new DataStorage();
    private final DataStorage errorStorage = new DataStorage();
    private final DataValidator dataValidator = new DataValidator();
    private final DataProcessor dataProcessor = new DataProcessor(this.dataStorage);

    public DataManager() {
    }


    @Override
    public final void addDataCollector(final ISensor sensor) {
        Utils.LOGGER.logInfo(MessageFormat.format("Adding {0} sensor to data collectors",
                                                  SensorType.getName(sensor.getSensorType())));
        this.dataCollectors.add(new DataCollector(sensor));
    }

    public final void collectAndStoreData() {
        for (DataCollector dataCollector : this.dataCollectors) {
            this.collectAndStoreData(dataCollector);
        }
    }

    @Override
    public List<SensorData> getAllData() {
        Utils.LOGGER.logInfo("Getting all data");
        return this.dataStorage.getAllStoredData();
    }

    private void collectAndStoreData(DataCollector dataCollector) {
        Utils.LOGGER.logInfo(MessageFormat.format("Collecting and storing data from {0} sensor",
                                                  SensorType.getName(dataCollector.getSensorType())));
        dataCollector.collectData();
        for (SensorData sensorData : dataCollector.getAllCollectedData()) {
            if (this.dataValidator.validate(this.dataProcessor, sensorData)) {
                this.dataStorage.storeData(sensorData);
            } else {
                this.errorStorage.storeData(sensorData);
            }
        }
        dataCollector.clearCollectedData();
    }

    @Override
    public final Map<SensorType, Double> calculateAverage() {
        return this.dataProcessor.calculateAverage();
    }

    @Override
    public final Map<SensorType, Double> calculateMax() {
        return this.dataProcessor.calculateMax();
    }

    @Override
    public final Map<SensorType, Double> calculateMin() {
        return this.dataProcessor.calculateMin();
    }

    @Override
    public final Map<SensorType, LocalDateTime> calculateLastCollectorTime() {
        return this.dataProcessor.calculateLastCollectorTime();
    }

    @Override
    public final Double calculateAverageBySensorType(final SensorType sensorType) {
        return this.dataProcessor.calculateAverageBySensorType(sensorType);
    }

    @Override
    public final Double calculateMaxBySensorType(final SensorType sensorType) {
        return this.dataProcessor.calculateMaxBySensorType(sensorType);
    }

    @Override
    public final Double calculateMinBySensorType(final SensorType sensorType) {
        return this.dataProcessor.calculateMinBySensorType(sensorType);
    }

    @Override
    public final LocalDateTime calculateLastCollectorTimeBySensorType(final SensorType sensorType) {
        return this.dataProcessor.calculateLastCollectorTimeBySensorType(sensorType);
    }

    @Override
    public final Optional<SensorData> calculateLastSensorDataBySensorType(final SensorType sensorType) {
        return this.dataProcessor.calculateLastSensorDataBySensorType(sensorType);
    }
}
