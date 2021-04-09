package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle = new Triangle(
                new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(0,0,1));

        assertEquals("Bad normal to plane", new Vector(-1,0,0),
                triangle.getNormal(new Point3D(0, 0, 1)));
    }
}