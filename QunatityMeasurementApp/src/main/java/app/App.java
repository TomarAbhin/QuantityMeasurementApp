package app;

import feetEquality.FeetMeasurementEquality.Feet;

public class App {
    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Equal: " + f1.equals(f2));
    }

}
