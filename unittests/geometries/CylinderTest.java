package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;


public class CylinderTest {

    /**
     * Test method for{@link Cylinder#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for case the point is on the "side" of cylinder
        Cylinder cylinder =
                new Cylinder(new Ray(new Point3D(0,0,0),new Vector(0,0,1)),1,10);
        assertEquals("Bad normal to Cylinder", new Vector(1,0,0),
                cylinder.getNormal(new Point3D(1,0,1)));
        // TC02: Test in case point is on one of the bases

        // TC03: Test in case point is on the other base


        // =============== Boundary Values Tests ==================
    }
}