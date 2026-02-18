package feetEqualityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import feetEquality.FeetMeasurementEquality.Feet;


public class FeetMeasurementEqualityTest {
	
    @Test
    public void testEquality_SameValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void testEquality_DifferentValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);
        assertFalse(f1.equals(f2));
    }

    @Test
    public void testEquality_NullComparison() {
        Feet f1 = new Feet(1.0);
        assertFalse(f1.equals(null));
    }

    @Test
    public void testEquality_DifferentClass() {
        Feet f1 = new Feet(1.0);
        String value = "1.0";
        assertFalse(f1.equals(value));
    }

    @Test
    public void testEquality_SameReference() {
        Feet f1 = new Feet(1.0);
        assertTrue(f1.equals(f1));
    }
}
