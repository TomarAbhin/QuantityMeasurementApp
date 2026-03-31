package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements", indexes = {
    @Index(name = "idx_operation", columnList = "operation"),
    @Index(name = "idx_this_measurement_type", columnList = "this_measurement_type"),
    @Index(name = "idx_is_error", columnList = "is_error")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value")
    private double thisValue;

    @Column(name = "this_unit", length = 50)
    private String thisUnit;

    @Column(name = "this_measurement_type", length = 50)
    private String thisMeasurementType;

    @Column(name = "that_value")
    private double thatValue;

    @Column(name = "that_unit", length = 50)
    private String thatUnit;

    @Column(name = "that_measurement_type", length = 50)
    private String thatMeasurementType;

    @Column(name = "operation", length = 50)
    private String operation;

    @Column(name = "result_string", length = 255)
    private String resultString;

    @Column(name = "result_value")
    private double resultValue;

    @Column(name = "result_unit", length = 50)
    private String resultUnit;

    @Column(name = "result_measurement_type", length = 50)
    private String resultMeasurementType;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "is_error")
    private boolean isError;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
