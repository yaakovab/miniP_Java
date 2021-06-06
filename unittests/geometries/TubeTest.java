package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {
    /**
     * Test method for{@link Tube#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for simple case  
        Tube tube = new Tube(
                new Ray(new Point3D(0,0,0),new Vector(0,0,1)),1);
        assertEquals("Bad normal to Tube", new Vector(1,0,0),
                tube.getNormal(new Point3D(1,0,1)));

        // =============== Boundary Values Tests ==================
        //TC11: Test in case the Point is facing the head of the ray
        assertEquals("Bad normal to Tube", new Vector(1,0,0),
                tube.getNormal(new Point3D(1,0,0)));
    }
}