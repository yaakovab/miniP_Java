package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Point3D class
 */
public class Point3DTest {
    /**
     * Test method for {@link Point3D#subtract(Point3D)}
     */
    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Test to check the result of subtracting a point from another point
        assertEquals("ERROR: Point - Point does not work correctly",
                new Vector(1,1,1),new Point3D(2,3,4).subtract(new Point3D(1,2,3)));
    }

    /**
     * Test method for {@link Point3D#add(Vector)}
     */
    @Test
    public void add() {
        Point3D point3D = new Point3D(1,2,3);
        Vector vector = new Vector(-1,-2,-3);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test to check the result of adding a vector to a point
        assertEquals("ERROR: Point + Vector does not work correctly", point3D.ZERO,point3D.add(vector));
    }

    @Test
    public void testDistance() {
        Point3D p1 = new  Point3D(1,2,3);
        Point3D p2 = new Point3D(7,8,9);

        assertEquals("distance is not correct", 10.392304845413264, p1.distance(p2),0.000000000001);

    }
}