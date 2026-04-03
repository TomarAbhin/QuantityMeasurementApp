package com.app.quantitymeasurement;

import com.app.quantitymeasurement.unit.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPS = 1e-6;

    // ---------------- LENGTH TESTS ----------------

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(f, i);
    }

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = f.convertTo(LengthUnit.INCH);
        assertEquals(12.0, result.getValue(), EPS);
    }

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = f.add(i, LengthUnit.FEET);
        assertEquals(2.0, result.getValue(), EPS);
    }

    // ---------------- WEIGHT TESTS ----------------

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(kg, g);
    }

    @Test
    void testWeightConversion() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = kg.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> result = kg.add(g, WeightUnit.KILOGRAM);
        assertEquals(2.0, result.getValue(), EPS);
    }

    // ---------------- CROSS CATEGORY SAFETY ----------------

    @Test
    void testCrossCategoryComparison() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertNotEquals(length, weight);
    }

    // ---------------- VALIDATION ----------------

    @Test
    void testNullUnitConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testInvalidValueConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    // ---------------- VOLUME TESTS ----------------

    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1.0, VolumeUnit.LITRE));
    }

    @Test
    void testEquality_LitreToMillilitre() {
        assertEquals(
                new Quantity<>(1.0, VolumeUnit.LITRE),
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
    }

    @Test
    void testEquality_LitreToGallon() {
        assertEquals(
                new Quantity<>(3.78541, VolumeUnit.LITRE),
                new Quantity<>(1.0, VolumeUnit.GALLON));
    }

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> ml = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, ml.getValue(), EPS);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
        assertEquals(3.78541, litre.getValue(), EPS);
    }

    @Test
    void testAddition_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(2.0, result.getValue(), EPS);
    }

    // ---------------- SUBTRACTION TESTS ----------------

    @Test
    void testSubtraction_SameUnit_Length() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(5.0, result.getValue(), 1e-6);
    }

    @Test
    void testSubtraction_CrossUnit_Length() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCH));
        assertEquals(9.5, result.getValue(), 1e-6);
    }

    @Test
    void testSubtraction_ResultNegative() {
        Quantity<WeightUnit> result = new Quantity<>(2.0, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));
        assertEquals(-3.0, result.getValue(), 1e-6);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSubtraction_CrossCategory_ShouldThrow() {
        Quantity<LengthUnit> l = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeightUnit> w = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class,
                () -> l.subtract((Quantity) w));
    }

    // ---------------- TEMPERATURE TESTS ----------------

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToKelvin() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(273.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT).convertTo(TemperatureUnit.CELSIUS);
        assertEquals(0.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_CelsiusToKelvin() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.KELVIN);
        assertEquals(273.15, result.getValue(), EPS);
    }

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> c1.add(c2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> c1.subtract(c2));
    }

    @Test
    void testTemperatureVsLengthIncompatibility() {
        assertNotEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, LengthUnit.FEET));
    }

    @Test
    void testOperationSupportMethods_TemperatureNotSupported() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
        assertFalse(TemperatureUnit.KELVIN.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthSupported() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightSupported() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_VolumeSupported() {
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
    }
}
