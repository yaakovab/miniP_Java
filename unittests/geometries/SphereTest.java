package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,1),2);

        assertEquals("Bad normal to triangle"
                , new Vector(0,0,1),sphere.getNormal(new Point3D(0,0,3)));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p3 = new Point3D(0.3955200580638223,-0.04653177153692,0.795260205237551);
        List<Point3D> res = sphere.findIntersections(new Ray(new Point3D(1.7,-0.2,0.12),
                new Vector(-1.7,0.2,0.88)));
        assertEquals("Wrong number of points", 1, res.size());
        assertEquals("Ray starts inside the sphere", List.of(p3), res);

        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(4,4,0),new Vector(1,1,0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p4 = new Point3D(0.408996298870721,0.784826242949144,0.186446758163892);
        res = sphere.findIntersections(new Ray(new Point3D(1.201918422813742,-0.008095880993877,0.979368882106913),
                new Vector(-1,1,-1)));
        assertEquals("Wrong number of points", 1, res.size());
        assertEquals("Ray starts inside the sphere", List.of(p4), res);
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(
                        new Point3D(1.201918422813742,-0.008095880993877,0.979368882106913),
                        new Vector(1, 2, 0))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        sphere = new Sphere(new Point3D(4, 0, 0), 1d);

        Point3D p5 = new Point3D(3, 0,0);
        Point3D p6 = new Point3D(5, 0, 0);
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p5, p6), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        p4 = new Point3D(5,0,0);
        res = sphere.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertEquals("Wrong number of points", 1, res.size());
        assertEquals("Ray starts inside the sphere", List.of(p4), res);
        // TC15: Ray starts inside (1 points)
        p4 = new Point3D(5,0,0);
        res = sphere.findIntersections(new Ray(new Point3D(3.5,0,0), new Vector(1,0,0)));
        assertEquals("Wrong number of points", 1, res.size());
        assertEquals("Ray starts inside the sphere", List.of(p4), res);
        // TC16: Ray starts at the center (1 points)
        p4 = new Point3D(5,0,0);
        res = sphere.findIntersections(new Ray(new Point3D(4,0,0), new Vector(1,0,0)));
        assertEquals("Wrong number of points", 1, res.size());
        assertEquals("Ray starts inside the sphere", List.of(p4), res);
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
                sphere.findIntersections(new Ray(new Point3D(5,0,0),new Vector(1,0,0))));
        // TC18: Ray starts after sphere (0 points)
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(5.5,0,0),new Vector(1,0,0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(3,0,-2),new Vector(0,0,1))));
        // TC20: Ray starts at the tangent point
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(3,0,0),new Vector(0,0,1))));
        // TC21: Ray starts after the tangent point
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(3,0,2),new Vector(0,0,1))));

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(2,0,0),new Vector(0,0,1))));

    }


}