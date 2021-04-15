package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    /**
     * Test method for {@link Triangle#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle = new Triangle(
                new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(0,0,1));

        Vector vectorNormal = new Vector(-1, 0, 0);
        //Test for one of 2 options: the vectorNormal is either going up or down
        Point3D p = new Point3D(0, 0, 1);
        assertTrue("Bad normal to triangle",
                triangle.getNormal(p).equals(vectorNormal) || triangle.getNormal(p).equals(vectorNormal.scale(-1)));
    }

    /**
     * Test methode for findIntersection for Triangle
     */
    @Test
    public void findIntersections() {
        Triangle triangle = new Triangle(new Point3D(0,1,0),new Point3D(0,4,0),new Point3D(1,2,3));

        // ============ Equivalence Partitions Tests ==============
        // TC01: inside polygon/triangle
        // TC02: outside against edge
        // TC03: outside against vertex

        // =============== Boundary Values Tests ==================
        // **** Group: The ray begins "before" plane
        // TC11: on edge
        // TC12: in vertex
        // TC13: on edge's continuation
    }
}