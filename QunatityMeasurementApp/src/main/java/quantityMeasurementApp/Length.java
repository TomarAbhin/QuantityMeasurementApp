package quantityMeasurementApp;


public class Length {

    private final double value;
    private final LengthUnit unit;

    // enum representing supported units with conversion to base unit
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    //constructor with validation
    public Length(double value, LengthUnit unit) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    //convert value to base unit(inches)
    private double convertToBaseUnit() {
        return this.value * unit.getConversionFactor();
    }

    //compare two Length objects
    public boolean compare(Length thatLength) {
        if (thatLength == null) return false;
        return Double.compare(this.convertToBaseUnit(),thatLength.convertToBaseUnit()) == 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Length that = (Length) o;

        return compare(that);
    }
}

