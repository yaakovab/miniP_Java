package primitives;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
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


}