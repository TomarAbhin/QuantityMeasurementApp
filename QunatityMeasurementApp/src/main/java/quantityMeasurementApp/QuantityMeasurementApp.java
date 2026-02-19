package quantityMeasurementApp;

public class QuantityMeasurementApp {
	
	//generic equality method
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		 return l1.equals(l2);
	}

	 //feet equality 
	 public static void demonstrateFeetEquality() {
	    Length l1 = new Length(1.0, Length.LengthUnit.FEET);
	    Length l2 = new Length(1.0, Length.LengthUnit.FEET);

	    System.out.println("Input: 1.0 feet and 1.0 feet");
	    System.out.println("Output: Equal (" + demonstrateLengthEquality(l1, l2) + ")");
	}

	//inches equality 
	public static void demonstrateInchesEquality() {
	   Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
	   Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
	   
	   System.out.println("Input: 1.0 inch and 1.0 inch");	
	   System.out.println("Output: Equal (" + demonstrateLengthEquality(l1, l2) + ")");
    }

	//feet to inches comparison
	public static void demonstrateFeetInchesComparison() {
	    Length feet = new Length(1.0, Length.LengthUnit.FEET);
	    Length inches = new Length(12.0, Length.LengthUnit.INCHES);

	    System.out.println("Input: 1.0 feet and 12.0 inches");
	    System.out.println("Output: Equal (" + demonstrateLengthEquality(feet, inches) + ")");
	}

    public static void main(String[] args) {
	    demonstrateFeetEquality();
	    demonstrateInchesEquality();
	    demonstrateFeetInchesComparison();
	}
}


