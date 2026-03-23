package Repository;

import java.util.List;

import Entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operationType);
    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
    int getTotalCount();
    void deleteAll();

    default String getPoolStatistics() { return "No pool statistics available"; }
    default void releaseResources()    {}
}