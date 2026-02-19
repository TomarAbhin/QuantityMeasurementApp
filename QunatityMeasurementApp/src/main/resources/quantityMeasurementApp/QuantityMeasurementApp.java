package quantityMeasurementApp;

public class QuantityMeasurementApp {

    //inner class to represent Feet measurement
    public static class Feet {

        private final double value;

        public Feet(double value) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value for Feet");
            }
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {

            //same reference check
            if (this == obj) return true;

            //null or different type check
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;

            //floating point safe comparison
            return Double.compare(this.value, other.value) == 0;
        }
    }

    //inner class to represent Inches measurement
    public static class Inches {

        private final double value;

        public Inches(double value) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value for Inches");
            }
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }

    //static method to demonstrate Feet equality check
    public static void demonstrateFeetEquality() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + f1.equals(f2) + ")");
    }

    //static method to demonstrate Inches equality check
    public static void demonstrateInchesEquality() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + i1.equals(i2) + ")");
    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
    }
}
