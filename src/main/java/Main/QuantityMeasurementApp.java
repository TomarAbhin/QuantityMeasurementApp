package Main;

import UtilityClasses.*;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable>
    void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        System.out.println("Compare " + q1 +
                " and " + q2 +
                " -> Equal: " + q1.equals(q2));
    }

    public static <U extends IMeasurable>
    void demonstrateConversion(Quantity<U> quantity, U target) {
        System.out.println("Convert " + quantity +
                " to " + target +
                " -> " + quantity.convertTo(target));
    }

    public static <U extends IMeasurable>
    void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U target) {
        System.out.println("Add " + q1 +
                " and " + q2 +
                " -> " + q1.add(q2, target));
    }

    public static void main(String[] args) {

        // ── LENGTH ──────────────────────────────────────────────────────────
        System.out.println("=== LENGTH ===");
        Quantity<LengthUnit> l1 = new Quantity<>(1.0,  LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCH);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCH);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        Quantity<LengthUnit> l4 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l6 = new Quantity<>(6.0,  LengthUnit.INCH);

        System.out.println("Subtract: "                    + l1.subtract(l2));
        System.out.println("Subtract (explicit inches): "  + l4.subtract(l6, LengthUnit.INCH));
        System.out.println("Divide: "
                + l1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

        // ── WEIGHT ──────────────────────────────────────────────────────────
        System.out.println("\n=== WEIGHT ===");
        Quantity<WeightUnit> w1 = new Quantity<>(1.0,    WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w5 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w8 = new Quantity<>(5.0,  WeightUnit.KILOGRAM);
        System.out.println("Weight division: " + w5.divide(w8));

        // ── VOLUME ──────────────────────────────────────────────────────────
        System.out.println("\n=== VOLUME ===");
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0,    VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        demonstrateEquality(v1, v2);

        // ── TEMPERATURE ─────────────────────────────────────────────────────
        System.out.println("\n=== TEMPERATURE ===");

        // Equality comparisons
        Quantity<TemperatureUnit> t1   = new Quantity<>(0.0,   TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2  = new Quantity<>(32.0,  TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> t3 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t4 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> t5 = new Quantity<>(273.15, TemperatureUnit.KELVIN);

        System.out.println("Temperature Equality Comparisons:");
        demonstrateEquality(t1,   t2);          // 0°C == 32°F  → true
        demonstrateEquality(t3, t4);         // 100°C == 212°F → true
        demonstrateEquality(t1,   t5);         // 0°C == 273.15 K → true

        // Conversions
        System.out.println("\nTemperature Conversions:");
        demonstrateConversion(t3,  TemperatureUnit.FAHRENHEIT);  // → 212°F
        demonstrateConversion(t2,   TemperatureUnit.CELSIUS);     // → 0°C
        demonstrateConversion(t5,  TemperatureUnit.CELSIUS);     // → 0°C
        demonstrateConversion(t1,    TemperatureUnit.KELVIN);      // → 273.15 K
        demonstrateConversion(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                TemperatureUnit.FAHRENHEIT);                        // → -40°F

        // Unsupported operations — demonstrate error handling
        System.out.println("\nUnsupported Operations (error handling):");

        try {
            t3.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
        } catch (UnsupportedOperationException e) {
            System.out.println("ADD caught: " + e.getMessage());
        }

        try {
            t3.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
        } catch (UnsupportedOperationException e) {
            System.out.println("SUBTRACT caught: " + e.getMessage());
        }

        try {
            t3.divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS));
        } catch (UnsupportedOperationException e) {
            System.out.println("DIVIDE caught: " + e.getMessage());
        }

        // Cross-category prevention
        System.out.println("\nCross-Category Prevention:");
        System.out.println("100°C equals 100 FEET? " +
                t3.equals(new Quantity<>(100.0, LengthUnit.FEET)));
        System.out.println("50°C equals 50 kg? " +
                new Quantity<>(50.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(50.0, WeightUnit.KILOGRAM)));
    }
}
