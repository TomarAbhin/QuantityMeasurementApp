package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

    @Autowired
    private IQuantityMeasurementService service;

    // ── POST Endpoints (Operations) ─────────────────────────────────────

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        logger.info("POST /compare - Comparing quantities");
        QuantityMeasurementDTO result = service.compareQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to a different unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input) {
        logger.info("POST /convert - Converting quantity");
        QuantityMeasurementDTO result = service.convertQuantity(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        logger.info("POST /add - Adding quantities");
        QuantityMeasurementDTO result = service.addQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        logger.info("POST /subtract - Subtracting quantities");
        QuantityMeasurementDTO result = service.subtractQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        logger.info("POST /divide - Dividing quantities");
        QuantityMeasurementDTO result = service.divideQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    // ── GET Endpoints (History & Stats) ─────────────────────────────────

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @PathVariable String operation) {
        logger.info("GET /history/operation/{} - Retrieving operation history", operation);
        List<QuantityMeasurementDTO> history = service.getOperationHistory(operation);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/type/{type}")
    @Operation(summary = "Get measurements by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementsByType(
            @PathVariable String type) {
        logger.info("GET /history/type/{} - Retrieving measurements by type", type);
        List<QuantityMeasurementDTO> measurements = service.getMeasurementsByType(type);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of successful operations by type")
    public ResponseEntity<Long> getOperationCount(
            @PathVariable String operation) {
        logger.info("GET /count/{} - Getting operation count", operation);
        long count = service.getOperationCount(operation);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        logger.info("GET /history/errored - Retrieving error history");
        List<QuantityMeasurementDTO> errors = service.getErrorHistory();
        return ResponseEntity.ok(errors);
    }
}
