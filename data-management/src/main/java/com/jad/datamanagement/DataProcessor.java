package com.jad.datamanagement;

import com.jad.sharedmodel.SensorData;
import com.jad.sharedmodel.SensorType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.DoubleStream;

class DataProcessor implements IDataProcessor {
    private final DataStorage dataStorage;

    public DataProcessor(final DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public final Map<SensorType, Double> calculateAverage() {
        Map<SensorType, Double> result = new java.util.HashMap<>();
        for (Map.Entry<SensorType, DoubleStream> entry : this.prepareCalculations().entrySet()) {
            result.put(entry.getKey(), entry.getValue().average().orElse(0.0));
        }
        return result;
    }

    private Map<SensorType, DoubleStream> prepareCalculations() {
        java.util.Map<SensorType, DoubleStream> result = new java.util.HashMap<>();
        for (SensorType sensorType : SensorType.values()) {
            result.put(sensorType, this.dataStorage.getAllDataBySensorType(sensorType)
                    .stream()
                    .map(SensorData::value)
                    .map(Integer::doubleValue)
                    .mapToDouble(Double::doubleValue));
        }
        return result;
    }

    @Override
    public final Map<SensorType, Double> calculateMax() {
        Map<SensorType, Double> result = new java.util.HashMap<>();
        for (Map.Entry<SensorType, DoubleStream> entry : this.prepareCalculations().entrySet()) {
            result.put(entry.getKey(), entry.getValue().max().orElse(0.0));
        }
        return result;
    }

    @Override
    public final Map<SensorType, Double> calculateMin() {
        Map<SensorType, Double> result = new java.util.HashMap<>();
        for (Map.Entry<SensorType, DoubleStream> entry : this.prepareCalculations().entrySet()) {
            result.put(entry.getKey(), entry.getValue().min().orElse(0.0));
        }
        return result;
    }

    @Override
    public final Map<SensorType, LocalDateTime> calculateLastCollectorTime() {
        Map<SensorType, LocalDateTime> result = new java.util.HashMap<>();
        for (SensorType sensorType : SensorType.values()) {
            result.put(sensorType, this.dataStorage.getAllDataBySensorType(sensorType)
                    .stream()
                    .map(SensorData::time)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now()));
        }
        return result;
    }

    @Override
    public final Double calculateAverageBySensorType(final SensorType sensorType) {
        return this.dataStorage.getAllDataBySensorType(sensorType)
                .stream()
                .map(SensorData::value)
                .map(Integer::doubleValue)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public final Double calculateMaxBySensorType(final SensorType sensorType) {
        return this.dataStorage.getAllDataBySensorType(sensorType)
                .stream()
                .map(SensorData::value)
                .map(Integer::doubleValue)
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
    }

    @Override
    public final Double calculateMinBySensorType(final SensorType sensorType) {
        return this.dataStorage.getAllDataBySensorType(sensorType)
                .stream()
                .map(SensorData::value)
                .map(Integer::doubleValue)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0.0);
    }

    @Override
    public final LocalDateTime calculateLastCollectorTimeBySensorType(final SensorType sensorType) {
        return this.dataStorage.getAllDataBySensorType(sensorType)
                .stream()
                .map(SensorData::time)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
    }

    @Override
    public Optional<SensorData> calculateLastSensorDataBySensorType(final SensorType sensorType) {
        return this.dataStorage.getAllDataBySensorType(sensorType)
                .stream()
                .max(java.util.Comparator.comparing(SensorData::time));
    }
}
