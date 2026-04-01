package com.app.quantitymeasurement;

import com.app.quantitymeasurement.dto.*;
import org.junit.jupiter.api.*;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuantityMeasurementAppApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String token;

    @BeforeEach
    void setUp() {
        if (token == null) {
            // Register
            SignupRequest signup = new SignupRequest();
            signup.setUsername("testuser");
            signup.setEmail("test@example.com");
            signup.setPassword("password");
            restTemplate.postForEntity("/api/auth/signup", signup, MessageResponse.class);

            // Login
            LoginRequest login = new LoginRequest();
            login.setUsername("testuser");
            login.setPassword("password");
            ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/signin", login, JwtResponse.class);
            if (loginResponse.getStatusCode().is2xxSuccessful()) {
                JwtResponse body = java.util.Objects.requireNonNull(loginResponse.getBody());
                token = body.getToken();
            }
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    // ── Helper ──────────────────────────────────────────────────────────

    private QuantityInputDTO createInput(double val1, String unit1, String type1,
                                          double val2, String unit2, String type2) {
        QuantityDTO q1 = new QuantityDTO(val1, unit1, type1);
        QuantityDTO q2 = new QuantityDTO(val2, unit2, type2);
        return new QuantityInputDTO(q1, q2);
    }

    // ── Application Context Test ────────────────────────────────────────

    @Test
    @Order(1)
    void contextLoads() {
        // Verifies Spring Boot application context loads successfully
    }

    // ── Compare Test ────────────────────────────────────────────────────

    @Test
    @Order(2)
    void testCompareQuantities() {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 12.0, "INCH", "LengthUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/compare", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/compare", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("compare", response.getBody().getOperation());
        assertEquals("true", response.getBody().getResultString());
        assertFalse(response.getBody().isError());
    }

    // ── Convert Test ────────────────────────────────────────────────────

    @Test
    @Order(3)
    void testConvertQuantity() {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 0.0, "INCH", "LengthUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/convert", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/convert", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("convert", response.getBody().getOperation());
        assertEquals(12.0, response.getBody().getResultValue(), 1e-6);
    }

    // ── Add Test ────────────────────────────────────────────────────────

    @Test
    @Order(4)
    void testAddQuantities() {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 12.0, "INCH", "LengthUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/add", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/add", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("add", response.getBody().getOperation());
        assertEquals(2.0, response.getBody().getResultValue(), 1e-6);
        assertEquals("FEET", response.getBody().getResultUnit());
    }

    // ── Subtract Test ───────────────────────────────────────────────────

    @Test
    @Order(5)
    void testSubtractQuantities() {
        QuantityInputDTO input = createInput(10.0, "FEET", "LengthUnit", 5.0, "FEET", "LengthUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/subtract", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/subtract", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("subtract", response.getBody().getOperation());
        assertEquals(5.0, response.getBody().getResultValue(), 1e-6);
    }

    // ── Divide Test ─────────────────────────────────────────────────────

    @Test
    @Order(6)
    void testDivideQuantities() {
        QuantityInputDTO input = createInput(10.0, "KILOGRAM", "WeightUnit", 5.0, "KILOGRAM", "WeightUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/divide", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/divide", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("divide", response.getBody().getOperation());
        assertEquals(2.0, response.getBody().getResultValue(), 1e-6);
    }

    // ── Operation History Test ──────────────────────────────────────────

    @Test
    @Order(7)
    void testGetOperationHistory() {
        ResponseEntity<List<QuantityMeasurementDTO>> response = restTemplate.exchange(
                "/api/v1/quantities/history/operation/compare",
                HttpMethod.GET, new HttpEntity<>(getHeaders()),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<QuantityMeasurementDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    // ── Measurement Type History Test ───────────────────────────────────

    @Test
    @Order(8)
    void testGetMeasurementsByType() {
        ResponseEntity<List<QuantityMeasurementDTO>> response = restTemplate.exchange(
                "/api/v1/quantities/history/type/LengthUnit",
                HttpMethod.GET, new HttpEntity<>(getHeaders()),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<QuantityMeasurementDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    // ── Operation Count Test ────────────────────────────────────────────

    @Test
    @Order(9)
    void testGetOperationCount() {
        ResponseEntity<Long> response = restTemplate.exchange(
                "/api/v1/quantities/count/compare",
                HttpMethod.GET, new HttpEntity<>(getHeaders()), Long.class);
        ResponseEntity<Long> response = restTemplate.getForEntity(
                "/api/v1/quantities/count/compare", Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() >= 1);
    }

    // ── Error Scenario: Cross-Category ──────────────────────────────────

    @Test
    @Order(10)
    void testAddIncompatibleTypes_Returns400() {
        QuantityInputDTO input = createInput(1.0, "FEET", "LengthUnit", 1.0, "KILOGRAM", "WeightUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(
                "/api/v1/quantities/add", HttpMethod.POST, entity, Map.class);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/quantities/add", input, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ── Error History Test ──────────────────────────────────────────────

    @Test
    @Order(11)
    void testGetErrorHistory() {
        ResponseEntity<List<QuantityMeasurementDTO>> response = restTemplate.exchange(
                "/api/v1/quantities/history/errored",
                HttpMethod.GET, new HttpEntity<>(getHeaders()),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<QuantityMeasurementDTO>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // ── Actuator Health Test ────────────────────────────────────────────

    @Test
    @Order(12)
    void testActuatorHealth() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/actuator/health", Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
    }

    // ── Weight Comparison Test ──────────────────────────────────────────

    @Test
    @Order(13)
    void testCompareWeightQuantities() {
        QuantityInputDTO input = createInput(1.0, "KILOGRAM", "WeightUnit", 1000.0, "GRAM", "WeightUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/compare", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/compare", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("true", response.getBody().getResultString());
    }

    // ── Temperature Conversion Test ─────────────────────────────────────

    @Test
    @Order(14)
    void testConvertTemperature() {
        QuantityInputDTO input = createInput(100.0, "CELSIUS", "TemperatureUnit", 0.0, "FAHRENHEIT", "TemperatureUnit");

        HttpEntity<QuantityInputDTO> entity = new HttpEntity<>(input, getHeaders());
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                "/api/v1/quantities/convert", HttpMethod.POST, entity, QuantityMeasurementDTO.class);
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(
                "/api/v1/quantities/convert", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(212.0, response.getBody().getResultValue(), 1e-6);
    }
}
