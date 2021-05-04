package primitives;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;


import geometries.Intersectable.GeoPoint;
import geometries.*;
import org.junit.Test;
/**
 * @author Yaacov
 * test for Ray class
 */
public class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
     */

    // ============ Equivalence Partitions Tests ==============
    //TC01  closet point in middle of list
    @Test
    public void testFindClosestPoint() {
        Ray ray = new Ray(new Point3D(1,1,1), new Vector(1,2,3))	;
        List<Point3D> points= new LinkedList<Point3D>();
        points.add(new Point3D(2,5,6));
        points.add(new Point3D(2,3,4));
        points.add(new Point3D(2,2,2));
        points.add(new Point3D(4,4,7));
        points.add(new Point3D(-2,-7,10));

        assertEquals("incorrect point", points.get(2), ray.findClosestPoint(points));


    // =============== Boundary Values Tests ==================
    //TC11 there is no intersections

        ray = new Ray(new Point3D(1,1,1), new Vector(1,2,3))	;
        points= new LinkedList<>();


        assertNull("there shouldn't be intersect points", ray.findClosestPoint(points));


    //TC12 the closet point at the beginning of list
       ray = new Ray(new Point3D(1,1,1), new Vector(1,2,3))	;
         points= new LinkedList<Point3D>();
        points.add(new Point3D(2,2,2));
        points.add(new Point3D(2,5,6));
        points.add(new Point3D(2,3,4));
        points.add(new Point3D(4,4,7));
        points.add(new Point3D(-2,-7,10));

        assertEquals("incorrect point", points.get(0), ray.findClosestPoint(points));


    //TC13 the closet point at the end of list
        ray = new Ray(new Point3D(1,1,1), new Vector(1,2,3))	;
        points= new LinkedList<Point3D>();
        points.add(new Point3D(2,5,6));
        points.add(new Point3D(2,3,4));
        points.add(new Point3D(4,4,7));
        points.add(new Point3D(-2,-7,10));
        points.add(new Point3D(2,2,2));


        assertEquals("incorrect point", points.get(4), ray.findClosestPoint(points));
    }

    /**
     * Test methode for {@link Ray#getClosestGeoPoint(List)}
     */
    @Test
    public void getClosestGeoPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01  closet geoPoint in middle of the list
            Ray ray = new Ray(new Point3D(0,0,1), new Vector(0,0,1));
            List<GeoPoint> geoPoints= new LinkedList<GeoPoint>();
            geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,4) ));
            geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,2) ));
            geoPoints.add(new GeoPoint(new Triangle(new Point3D(-1,0,5),new Point3D(-1,-2,5),
                    new Point3D(2,0,5)),new Point3D(0,0,5)));


            assertEquals("incorrect geoPoint", geoPoints.get(1), ray.getClosestGeoPoint(geoPoints));

        // =============== Boundary Values Tests ==================
        //TC11 there is no intersections

        ray = new Ray(new Point3D(1,1,1), new Vector(1,2,3))	;
        geoPoints= new LinkedList<>();
        assertNull("there shouldn't be intersect points", ray.getClosestGeoPoint(geoPoints));

        //TC12 the closet point at the beginning of list
        ray = new Ray(new Point3D(0,0,1), new Vector(0,0,1));
        geoPoints= new LinkedList<GeoPoint>();
        geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,2) ));
        geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,4) ));
        geoPoints.add(new GeoPoint(new Triangle(new Point3D(-1,0,5),new Point3D(-1,-2,5),
                new Point3D(2,0,5)),new Point3D(0,0,5)));

        assertEquals("incorrect geoPoint", geoPoints.get(0), ray.getClosestGeoPoint(geoPoints));

        //TC13 the closet point at the end of list
        geoPoints= new LinkedList<GeoPoint>();
        geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,4) ));
        geoPoints.add(new GeoPoint(new Triangle(new Point3D(-1,0,5),new Point3D(-1,-2,5),
                new Point3D(2,0,5)),new Point3D(0,0,5)));
        geoPoints.add(new GeoPoint(new Sphere(new Point3D(0,0,3),1) ,new Point3D(0,0,2) ));

        assertEquals("incorrect geoPoint", geoPoints.get(2), ray.getClosestGeoPoint(geoPoints));
    }
}