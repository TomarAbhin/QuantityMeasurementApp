package UtilityClasses;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        final Function<Double, Double> CELSIUS_TO_CELSIUS = (celsius) -> celsius;

        @Override
        public double convertToBaseUnit(double value) {
            return CELSIUS_TO_CELSIUS.apply(value);          // celsius → celsius
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return CELSIUS_TO_CELSIUS.apply(baseValue);      // celsius → celsius
        }

        @Override
        public String getUnitName() { return "CELSIUS"; }
    },

    FAHRENHEIT {
        // Fahrenheit → Celsius
        final Function<Double, Double> FAHRENHEIT_TO_CELSIUS =
                (f) -> (f - 32.0) * 5.0 / 9.0;

        // Celsius → Fahrenheit
        final Function<Double, Double> CELSIUS_TO_FAHRENHEIT =
                (c) -> (c * 9.0 / 5.0) + 32.0;

        @Override
        public double convertToBaseUnit(double value) {
            return FAHRENHEIT_TO_CELSIUS.apply(value);       // fahrenheit → celsius
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return CELSIUS_TO_FAHRENHEIT.apply(baseValue);   // celsius → fahrenheit
        }

        @Override
        public String getUnitName() { return "FAHRENHEIT"; }
    },

    KELVIN {
        // Kelvin → Celsius
        final Function<Double, Double> KELVIN_TO_CELSIUS =
                (k) -> k - 273.15;

        // Celsius → Kelvin
        final Function<Double, Double> CELSIUS_TO_KELVIN =
                (c) -> c + 273.15;

        @Override
        public double convertToBaseUnit(double value) {
            return KELVIN_TO_CELSIUS.apply(value);           // kelvin → celsius
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return CELSIUS_TO_KELVIN.apply(baseValue);       // celsius → kelvin
        }

        @Override
        public String getUnitName() { return "KELVIN"; }
    };


    @Override
    public double getConversionFactor() {
        return 1.0;   
     }
    SupportsArithmetic supportsArithmetic = () -> false;

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation +". Temperature values cannot be meaningfully added, subtracted, or divided " +
                "as absolute quantities. Only equality comparison and unit conversion are supported.");
    }
}
