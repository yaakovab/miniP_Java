package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public void testFindIntersection() {

        // ============ Equivalence Partitions Tests ==============


        // TC 1: Ray intersect the triangle inside
        Point3D A=new Point3D(1,0,2);
        Point3D B=new Point3D(3,0,2);
        Point3D C=new Point3D(2,2,2);

        Triangle triangle=new Triangle(A, B, C);
        Vector v= new Vector(0, 0, 1);
        Ray ray=new Ray(new Point3D(2,1,0), v);
        Point3D P = new Point3D(2,1,2); // expected intersect point
        List<Point3D> result = triangle.findIntersections(ray);
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray intersects the plane", List.of(P), result);


        // TC 2: Ray not intersect the triangle and goes against edge
        ray=new Ray(new Point3D(1,-1,0), v);
        result = triangle.findIntersections(ray);
        assertNull("ray shouldnt intersect, ray against edge", result);

        // TC 3: Ray not intersect the triangle and goes against vertax
        ray=new Ray(new Point3D(2,-3,0), v);
        result = triangle.findIntersections(ray);
        assertNull("ray shouldnt intersect, ray against vertax", result);


        // =============== Boundary Values Tests ==================

        // TC 10: ray goes through edge
        ray=new Ray(new Point3D(2,0,0), v);
        result = triangle.findIntersections(ray);
        assertNull("ray shouldnt intersect, ray against vertax", result);

        // TC 11: ray goes through vertax
        ray=new Ray(new Point3D(3,0,0), v);
        result = triangle.findIntersections(ray);
        assertNull("ray shouldnt intersect, ray against vertax", result);

        // TC 11: ray goes through vertax
        ray=new Ray(new Point3D(4,0,0), v);
        result = triangle.findIntersections(ray);
        assertNull("ray shouldnt intersect, ray against vertax", result);

    }

}