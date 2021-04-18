package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    @Test
    public void findIntersections() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: not all shapes are intersected with Ray
        Sphere sphere = new Sphere(new Point3D(2,0,0),1);
        Plane plane = new Plane(new Point3D(-5,0,0),new Point3D(0,0,4),new Point3D(0,-4,0));
        Triangle triangle = new Triangle(new Point3D(6,0,0),new Point3D(4,0,0),new Point3D(2.38107,5.79587,0));
        Geometries geometries = new Geometries(sphere,plane,triangle);
        List<Point3D> result = geometries.findIntersections(new Ray(new Point3D(0,4,0),new Vector(2.732115775904636,-4.492163336027315,0.470937088518219)));
        assertEquals("Wrong number of points", 3, result.size());

        // =============== Boundary Values Tests ==================
        // TC11: Collection is empty
        Geometries geometries1 = new Geometries();
        assertNull("Collection of shapes is empty",
                geometries1.findIntersections(new Ray(new Point3D(0,4,0),new Vector(2.732115775904636,-4.492163336027315,0.470937088518219))));
        // TC12: all shapes intersected with Ray
        Triangle triangle1 = new Triangle(new Point3D(3.394228564552314,-3.933133206886659,0),new Point3D(5.490624249175983,-2.801760059740879,0),new Point3D(2.37669835688477,-0.470644059061225,0.797867481221467));
        geometries = new Geometries(sphere,plane,triangle1);
        result = geometries.findIntersections(new Ray(new Point3D(0,4,0),new Vector(2.732115775904636,-4.492163336027315,0.470937088518219)));
        assertEquals("Wrong number of points", 4, result.size());

        // TC13: no shape is intersected with Ray
        sphere = new Sphere(new Point3D(1,0,0),1);
        plane = new Plane(new Point3D(0,0,4),new Point3D(0,0,0),new Point3D(-3,0,0));
        triangle = new Triangle(new Point3D(0,-1,0),new Point3D(0,-2,0),new Point3D(0,-2,2));
        geometries = new Geometries(sphere,plane,triangle);
        assertNull("no shape is intersected with Ray",
                geometries.findIntersections(new Ray(new Point3D(4,0,0),new Vector(1,0,0))));
        // TC14: only 1 shape is intersected
        List<Point3D> result2 = geometries.findIntersections(new Ray(new Point3D(4,0,0),new Vector(-40,-1,-1)));
        assertEquals("Wrong number of points", 2, result2.size());

    }
}