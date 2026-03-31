package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.quantitymeasurement.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@Import(SecurityConfig.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    // ── Helper ──────────────────────────────────────────────────────────

    private QuantityInputDTO createInput(double val1, String unit1, String type1,
                                          double val2, String unit2, String type2) {
        QuantityDTO q1 = new QuantityDTO(val1, unit1, type1);
        QuantityDTO q2 = new QuantityDTO(val2, unit2, type2);
        return new QuantityInputDTO(q1, q2);
    }

    private QuantityMeasurementDTO createResult(String operation, double thisVal, String thisUnit,
                                                 String thisType, double thatVal, String thatUnit,
                                                 String thatType) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setOperation(operation);
        dto.setThisValue(thisVal);
        dto.setThisUnit(thisUnit);
        dto.setThisMeasurementType(thisType);
        dto.setThatValue(thatVal);
        dto.setThatUnit(thatUnit);
        dto.setThatMeasurementType(thatType);
        return dto;
    }

    // ── Compare Tests ───────────────────────────────────────────────────

    @Test
    @WithMockUser
    void testCompareQuantities_Success() throws Exception {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 12.0, "INCH", "LengthUnit");
        QuantityMeasurementDTO result = createResult("compare", 1.0, "FEET", "LengthUnit",
                12.0, "INCH", "LengthUnit");
        result.setResultString("true");

        Mockito.when(service.compareQuantities(any(), any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("compare"))
                .andExpect(jsonPath("$.resultString").value("true"));

        Mockito.verify(service).compareQuantities(any(), any());
    }

    // ── Add Tests ───────────────────────────────────────────────────────

    @Test
    @WithMockUser
    void testAddQuantities_Success() throws Exception {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 12.0, "INCH", "LengthUnit");
        QuantityMeasurementDTO result = createResult("add", 1.0, "FEET", "LengthUnit",
                12.0, "INCH", "LengthUnit");
        result.setResultValue(2.0);
        result.setResultUnit("FEET");
        result.setResultMeasurementType("LengthUnit");

        Mockito.when(service.addQuantities(any(), any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"));

        Mockito.verify(service).addQuantities(any(), any());
    }

    // ── Convert Tests ───────────────────────────────────────────────────

    @Test
    @WithMockUser
    void testConvertQuantity_Success() throws Exception {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 0.0, "INCH", "LengthUnit");
        QuantityMeasurementDTO result = createResult("convert", 1.0, "FEET", "LengthUnit",
                0.0, "INCH", "LengthUnit");
        result.setResultValue(12.0);

        Mockito.when(service.convertQuantity(any(), any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(12.0));
    }

    // ── History Tests ───────────────────────────────────────────────────

    @Test
    @WithMockUser
    void testGetOperationHistory() throws Exception {
        QuantityMeasurementDTO dto = createResult("compare", 1.0, "FEET", "LengthUnit",
                12.0, "INCH", "LengthUnit");
        List<QuantityMeasurementDTO> history = Arrays.asList(dto);

        Mockito.when(service.getOperationHistory("compare")).thenReturn(history);

        mockMvc.perform(get("/api/v1/quantities/history/operation/compare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("compare"));
    }

    // ── Count Tests ─────────────────────────────────────────────────────

    @Test
    @WithMockUser
    void testGetOperationCount() throws Exception {
        Mockito.when(service.getOperationCount("compare")).thenReturn(5L);

        mockMvc.perform(get("/api/v1/quantities/count/compare"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // ── Error History Tests ─────────────────────────────────────────────

    @Test
    @WithMockUser
    void testGetErrorHistory() throws Exception {
        QuantityMeasurementDTO errorDto = new QuantityMeasurementDTO();
        errorDto.setError(true);
        errorDto.setErrorMessage("Test error");
        List<QuantityMeasurementDTO> errors = Arrays.asList(errorDto);

        Mockito.when(service.getErrorHistory()).thenReturn(errors);

        mockMvc.perform(get("/api/v1/quantities/history/errored"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].error").value(true))
                .andExpect(jsonPath("$[0].errorMessage").value("Test error"));
    }

    // ── Validation Error Test ───────────────────────────────────────────

    @Test
    @WithMockUser
    void testInvalidInput_Returns400() throws Exception {
        // Missing required fields
        String invalidJson = "{\"thisQuantityDTO\":{},\"thatQuantityDTO\":{}}";

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
