package com.app.quantitymeasurement.dto;

import com.app.quantitymeasurement.unit.IMeasurable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    @NotNull(message = "Value must not be null")
    private Double value;

    @NotEmpty(message = "Unit must not be empty")
    private String unit;

    @NotEmpty(message = "Measurement type must not be empty")
    private String measurementType;

    /**
     * Validates that the unit is valid for the specified measurement type.
     * This is used to ensure that the unit is a valid enum constant
     * for the given measurement type before processing.
     */
    public boolean isValidUnit() {
        try {
            IMeasurable.getUnitInstance(measurementType, unit);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
