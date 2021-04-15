package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Test for geometries.Plane class
 */
public class PlaneTest {

    private Vector vectorNormal;

    /**
     * Test method for {@link Plane#Plane(Point3D, Point3D, Point3D)}
     */
    @Test
    public void testConstructor(){
        // =============== Boundary Values Tests ==================
        //TC01: Test in case 2 first points of the plane are the same
        try{
            new Plane(new Point3D(0,0,1),new Point3D(0,0,1),new Point3D(1,0,0));
            fail("Constructed a plane with 2 first points are the same");
        }catch (IllegalArgumentException e){}

        //TC02: Test in case all 3 points are in the same line
        try{
            new Plane(new Point3D(1,0,0),new Point3D(2,0,0),new Point3D(3,0,0));
            fail("Constructed a plane that is actually a line");
        }catch (IllegalArgumentException e){}
    }


    /**
     * Test method for{@link Plane#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane plane = new Plane(
                new Point3D(0,0,1),new Point3D(1,0,0),new Point3D(0,1,0));
        double sqrt3 = Math.sqrt(1d / 3);
        vectorNormal = new Vector(sqrt3, sqrt3, sqrt3);
        //Test for one of 2 options: the vectorNormal is either going up or down
        Point3D p = new Point3D(0, 0, 1);
        assertTrue("Bad normal to plane",
                plane.getNormal(p).equals(vectorNormal) || plane.getNormal(p).equals(vectorNormal.scale(-1)));
    }
}