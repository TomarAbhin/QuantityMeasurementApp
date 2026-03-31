package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.*;
import com.app.quantitymeasurement.dto.*;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    @Autowired
    private QuantityMeasurementRepository repository;

    // ── Helper: Convert QuantityDTO to QuantityModel ────────────────────

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> QuantityModel<U> convertDtoToModel(QuantityDTO dto) {
        U unit = (U) IMeasurable.getUnitInstance(dto.getMeasurementType(), dto.getUnit());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    // ── Helper: Build entity from input DTOs ────────────────────────────

    private QuantityMeasurementEntity buildEntity(QuantityDTO q1, QuantityDTO q2, String operation) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());
        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());
        entity.setOperation(operation);
        entity.setError(false);
        return entity;
    }

    // ── Helper: Build error entity ──────────────────────────────────────

    private QuantityMeasurementEntity buildErrorEntity(QuantityDTO q1, QuantityDTO q2,
                                                        String operation, String errorMessage) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        if (q1 != null) {
            entity.setThisValue(q1.getValue());
            entity.setThisUnit(q1.getUnit());
            entity.setThisMeasurementType(q1.getMeasurementType());
        }
        if (q2 != null) {
            entity.setThatValue(q2.getValue());
            entity.setThatUnit(q2.getUnit());
            entity.setThatMeasurementType(q2.getMeasurementType());
        }
        entity.setOperation(operation);
        entity.setError(true);
        entity.setErrorMessage(errorMessage);
        return entity;
    }

    // ── Compare ─────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO compareQuantities(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "compare Error: Cannot compare different measurement categories: "
                        + q1.getMeasurementType() + " and " + q2.getMeasurementType());

            QuantityModel model1 = convertDtoToModel(q1);
            QuantityModel model2 = convertDtoToModel(q2);
            boolean result = model1.getQuantity().equals(model2.getQuantity());

            QuantityMeasurementEntity entity = buildEntity(q1, q2, "compare");
            entity.setResultString(String.valueOf(result));
            repository.save(entity);

            return QuantityMeasurementDTO.fromEntity(entity);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Compare failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = buildErrorEntity(q1, q2, "compare", e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("compare Error: " + e.getMessage(), e);
        }
    }

    // ── Convert ─────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO convertQuantity(QuantityDTO source, QuantityDTO targetUnitDto) {
        try {
            QuantityModel model = convertDtoToModel(source);
            IMeasurable targetUnit = IMeasurable.getUnitInstance(
                    targetUnitDto.getMeasurementType(), targetUnitDto.getUnit());
            Quantity converted = model.getQuantity().convertTo(targetUnit);

            QuantityMeasurementEntity entity = buildEntity(source, targetUnitDto, "convert");
            double roundedValue = Math.round(converted.getValue() * 100.0) / 100.0;
            entity.setResultValue(roundedValue);
            repository.save(entity);

            return QuantityMeasurementDTO.fromEntity(entity);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Convert failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = buildErrorEntity(source, targetUnitDto, "convert", e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("convert Error: " + e.getMessage(), e);
        }
    }

    // ── Add ─────────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO addQuantities(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "add Error: Cannot perform arithmetic between different measurement categories: "
                        + q1.getMeasurementType() + " and " + q2.getMeasurementType());

            QuantityModel model1 = convertDtoToModel(q1);
            QuantityModel model2 = convertDtoToModel(q2);
            Quantity result = model1.getQuantity().add(model2.getQuantity());

            QuantityMeasurementEntity entity = buildEntity(q1, q2, "add");
            double roundedValue = Math.round(result.getValue() * 100.0) / 100.0;
            entity.setResultValue(roundedValue);
            entity.setResultUnit(result.getUnit().getUnitName());
            entity.setResultMeasurementType(result.getUnit().getMeasurementType());
            repository.save(entity);

            return QuantityMeasurementDTO.fromEntity(entity);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Add failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = buildErrorEntity(q1, q2, "add", e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("add Error: " + e.getMessage(), e);
        }
    }

    // ── Subtract ────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO subtractQuantities(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "subtract Error: Cannot perform arithmetic between different measurement categories: "
                        + q1.getMeasurementType() + " and " + q2.getMeasurementType());

            QuantityModel model1 = convertDtoToModel(q1);
            QuantityModel model2 = convertDtoToModel(q2);
            Quantity result = model1.getQuantity().subtract(model2.getQuantity());

            QuantityMeasurementEntity entity = buildEntity(q1, q2, "subtract");
            double roundedValue = Math.round(result.getValue() * 100.0) / 100.0;
            entity.setResultValue(roundedValue);
            entity.setResultUnit(result.getUnit().getUnitName());
            entity.setResultMeasurementType(result.getUnit().getMeasurementType());
            repository.save(entity);

            return QuantityMeasurementDTO.fromEntity(entity);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Subtract failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = buildErrorEntity(q1, q2, "subtract", e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("subtract Error: " + e.getMessage(), e);
        }
    }

    // ── Divide ──────────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO divideQuantities(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException(
                        "divide Error: Cannot perform arithmetic between different measurement categories: "
                        + q1.getMeasurementType() + " and " + q2.getMeasurementType());

            QuantityModel model1 = convertDtoToModel(q1);
            QuantityModel model2 = convertDtoToModel(q2);
            double result = model1.getQuantity().divide(model2.getQuantity());

            QuantityMeasurementEntity entity = buildEntity(q1, q2, "divide");
            double roundedValue = Math.round(result * 100.0) / 100.0;
            entity.setResultValue(roundedValue);
            repository.save(entity);

            return QuantityMeasurementDTO.fromEntity(entity);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Divide failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = buildErrorEntity(q1, q2, "divide", e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("divide Error: " + e.getMessage(), e);
        }
    }

    // ── History & Stats ─────────────────────────────────────────────────

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        List<QuantityMeasurementEntity> entities = repository.findByOperation(operation.toLowerCase());
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> entities = repository.findByThisMeasurementType(measurementType);
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toLowerCase());
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        List<QuantityMeasurementEntity> entities = repository.findByIsErrorTrue();
        return QuantityMeasurementDTO.fromEntityList(entities);
    }
}
