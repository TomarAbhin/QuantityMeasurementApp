package quantityMeasurementAppTest;

import UtilityClasses.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPS = 1e-6;

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertEquals(t1, t2);
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(t1, t2);
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
        assertEquals(fahrenheit, celsius);
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> t = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertEquals(t, t);
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        Quantity<TemperatureUnit> c50 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertEquals(122.0, c50.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);

        Quantity<TemperatureUnit> cNeg20 = new Quantity<>(-20.0, TemperatureUnit.CELSIUS);
        assertEquals(-4.0, cNeg20.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);

        Quantity<TemperatureUnit> c100 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertEquals(212.0, c100.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);

        Quantity<TemperatureUnit> c37 = new Quantity<>(37.0, TemperatureUnit.CELSIUS);
        assertEquals(98.6, c37.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        Quantity<TemperatureUnit> f122 = new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(50.0, f122.convertTo(TemperatureUnit.CELSIUS).getValue(), EPS);

        Quantity<TemperatureUnit> fNeg4 = new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(-20.0, fNeg4.convertTo(TemperatureUnit.CELSIUS).getValue(), EPS);

        Quantity<TemperatureUnit> f212 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(100.0, f212.convertTo(TemperatureUnit.CELSIUS).getValue(), EPS);

        Quantity<TemperatureUnit> f986 = new Quantity<>(98.6, TemperatureUnit.FAHRENHEIT);
        assertEquals(37.0, f986.convertTo(TemperatureUnit.CELSIUS).getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> original = new Quantity<>(37.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> roundTrip = original
                .convertTo(TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(original.getValue(), roundTrip.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = celsius.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(50.0, result.getValue(), EPS);
        assertEquals(TemperatureUnit.CELSIUS, result.getUnit());
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> zero = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = zero.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_NegativeValues() {
        Quantity<TemperatureUnit> cNeg40 = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        assertEquals(-40.0, cNeg40.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);

        Quantity<TemperatureUnit> absZero = new Quantity<>(-273.15, TemperatureUnit.CELSIUS);
        assertEquals(-459.67, absZero.convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> c1000 = new Quantity<>(1000.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = c1000.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(1832.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.subtract(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.divide(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        UnsupportedOperationException ex =
                assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().toLowerCase().contains("temperature"));
        assertTrue(ex.getMessage().toLowerCase().contains("add"));
    }

    @Test
    void testTemperatureVsLengthIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);
        assertNotEquals(temp, length);
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<WeightUnit> weight = new Quantity<>(50.0, WeightUnit.KILOGRAM);
        assertNotEquals(temp, weight);
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        Quantity<TemperatureUnit> temp = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        Quantity<VolumeUnit> volume = new Quantity<>(25.0, VolumeUnit.LITRE);
        assertNotEquals(temp, volume);
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    void testIMeasurableInterface_Evolution_BackwardCompatible() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(LengthUnit.INCH.supportsArithmetic());
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
        assertTrue(WeightUnit.GRAM.supportsArithmetic());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
        assertTrue(VolumeUnit.MILLILITRE.supportsArithmetic());

        Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(2.0, f.add(i, LengthUnit.FEET).getValue(), EPS);
    }

    @Test
    void testTemperatureUnit_NonLinearConversion() {
        Quantity<TemperatureUnit> zero = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = zero.convertTo(TemperatureUnit.FAHRENHEIT);
        assertNotEquals(0.0, result.getValue(), EPS);
        assertEquals(32.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureUnit_AllConstants() {
        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
        assertNotNull(TemperatureUnit.KELVIN);
    }

    @Test
    void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS", TemperatureUnit.CELSIUS.getUnitName());
        assertEquals("FAHRENHEIT", TemperatureUnit.FAHRENHEIT.getUnitName());
        assertEquals("KELVIN", TemperatureUnit.KELVIN.getUnitName());
    }

    @Test
    void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionFactor(), EPS);
        assertEquals(1.0, TemperatureUnit.FAHRENHEIT.getConversionFactor(), EPS);
        assertEquals(1.0, TemperatureUnit.KELVIN.getConversionFactor(), EPS);
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, (TemperatureUnit) null));
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> t = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertNotEquals(null, t);
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertNotEquals(t1, t2);
    }

    @Test
    void testTemperatureBackwardCompatibility_UC1_Through_UC13() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(feet, inch);
        assertEquals(2.0, feet.add(inch, LengthUnit.FEET).getValue(), EPS);

        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(kg, gram);
        assertEquals(2.0, kg.add(gram, WeightUnit.KILOGRAM).getValue(), EPS);

        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(litre, ml);
        assertEquals(2.0, litre.add(ml, VolumeUnit.LITRE).getValue(), EPS);

        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    @Test
    void testTemperatureConversionPrecision_Epsilon() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(celsius, fahrenheit);
    }

    @Test
    void testTemperatureConversionEdgeCase_VerySmallDifference() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(0.000000001, TemperatureUnit.CELSIUS);
        assertEquals(t1, t2);
    }

    @Test
    void testTemperatureEnumImplementsIMeasurable() {
        assertTrue(TemperatureUnit.CELSIUS instanceof IMeasurable);
        assertTrue(TemperatureUnit.FAHRENHEIT instanceof IMeasurable);
        assertTrue(TemperatureUnit.KELVIN instanceof IMeasurable);
    }

    @Test
    void testTemperatureDefaultMethodInheritance() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(LengthUnit.YARDS.supportsArithmetic());
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
        assertTrue(WeightUnit.POUND.supportsArithmetic());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
        assertTrue(VolumeUnit.GALLON.supportsArithmetic());
    }

    @Test
    void testTemperatureCrossUnitAdditionAttempt() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(50.0, TemperatureUnit.FAHRENHEIT);
        assertThrows(UnsupportedOperationException.class, () -> celsius.add(fahrenheit));
    }

    @Test
    void testTemperatureValidateOperationSupport_MethodBehavior() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("addition"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.FAHRENHEIT.validateOperationSupport("subtraction"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.KELVIN.validateOperationSupport("divide"));
    }

    @Test
    void testTemperatureIntegrationWithGenericQuantity() {
        Quantity<TemperatureUnit> t = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertNotNull(t.toString());
        assertTrue(t.toString().contains("100.0"));

        assertEquals(100.0, t.getValue(), EPS);
        assertEquals(TemperatureUnit.CELSIUS, t.getUnit());

        Quantity<TemperatureUnit> converted = t.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, converted.getValue(), EPS);
        assertEquals(TemperatureUnit.FAHRENHEIT, converted.getUnit());

        Quantity<TemperatureUnit> f212 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(t, f212);
    }
}