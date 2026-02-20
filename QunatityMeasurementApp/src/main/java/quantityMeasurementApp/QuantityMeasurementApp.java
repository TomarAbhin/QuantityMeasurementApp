package quantityMeasurementApp;

public class QuantityMeasurementApp {

    // Create a generic method to demonstrate Length equality check
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length1.equals(length2);
    }

    // Create a static method to take parameters and demonstrate equality check
    public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1,
                                                      double value2, Length.LengthUnit unit2) {

        Length length1 = new Length(value1, unit1);
        Length length2 = new Length(value2, unit2);

        boolean result = demonstrateLengthEquality(length1, length2);

        System.out.println("Input: " + value1 + " " + unit1 +
                        " and " + value2 + " " + unit2);

        System.out.println("Output: Equal (" + result + ")");
        System.out.println();

        return result;
    }

    // Main method to demonstrate extended unit support
    public static void main(String[] args) {

        //demonstrate Feet and Inches comparison
        demonstrateLengthComparison(1.0, Length.LengthUnit.FEET,
                                    12.0, Length.LengthUnit.INCHES);

        //demonstrate Yards and Inches comparison
        demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS,
                                    36.0, Length.LengthUnit.INCHES);

        //demonstrate Centimeters and Inches comparison
        demonstrateLengthComparison(100.0, Length.LengthUnit.CENTIMETERS,
                                    39.3701, Length.LengthUnit.INCHES);

        //demonstrate Feet and Yards comparison
        demonstrateLengthComparison(3.0, Length.LengthUnit.FEET,
                                    1.0, Length.LengthUnit.YARDS);

        //demonstrate Centimeters and Feet comparison
        demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS,
                                    1.0, Length.LengthUnit.FEET);
    }
}


