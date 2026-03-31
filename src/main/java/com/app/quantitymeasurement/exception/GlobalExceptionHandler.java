package com.app.quantitymeasurement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ── Handle Validation Errors (MethodArgumentNotValidException) ───────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        logger.error("Validation error: {}", ex.getMessage());

        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        Map<String, Object> body = buildErrorBody(
                HttpStatus.BAD_REQUEST,
                "Quantity Measurement Error",
                errorMessages,
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // ── Handle QuantityMeasurementException ──────────────────────────────

    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<Map<String, Object>> handleQuantityException(
            QuantityMeasurementException ex, HttpServletRequest request) {

        logger.error("Quantity measurement error: {}", ex.getMessage());

        Map<String, Object> body = buildErrorBody(
                HttpStatus.BAD_REQUEST,
                "Quantity Measurement Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // ── Handle All Other Exceptions ─────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(
            Exception ex, HttpServletRequest request) {

        logger.error("Internal error: {}", ex.getMessage(), ex);

        Map<String, Object> body = buildErrorBody(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ── Helper ──────────────────────────────────────────────────────────

    private Map<String, Object> buildErrorBody(HttpStatus status, String error,
                                                String message, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return body;
    }
}
