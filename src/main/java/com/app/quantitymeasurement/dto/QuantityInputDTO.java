package com.app.quantitymeasurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    @Valid
    @NotNull(message = "thisQuantityDTO must not be null")
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "thatQuantityDTO must not be null")
    private QuantityDTO thatQuantityDTO;

    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnits() {
        if (thisQuantityDTO == null || thatQuantityDTO == null) return true;
        return thisQuantityDTO.isValidUnit() && thatQuantityDTO.isValidUnit();
    }
}
