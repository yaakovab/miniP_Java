package primitives;
import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Dan
 */
public class VectorTests {

    @Test
    public void subtract() {
    }

    @Test
    public void add() {
    }

    @Test
    public void scale() {
    }

    /**
     * Test method for {@link Vector#Vector constructors}
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        //TC01: Test for zero vector in all three kinds of Vector constructors
        try {
            new Vector(0, 0, 0);
            fail("constructed the zero vector which is not allowed");
        } catch (IllegalArgumentException e) {

        }

        try{
            new Vector(new Point3D(0,0,0));
            fail("constructed the zero vector which is not allowed");
        } catch (IllegalArgumentException e) {}

        try{
            new Vector(new Coordinate(0),new Coordinate(0),new Coordinate(0));
            fail("constructed the zero vector which is not allowed");
        } catch (IllegalArgumentException e) {

        }


    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that dot product of 2 orthogonal vectors is zero
        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero",isZero(v1.dotProduct(v3)));

        //TC02: Test that result of dot product is right value
        assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
       // assertThrows("crossProduct() for parallel vectors does not throw an exception",
             //   IllegalArgumentException.class, () -> v1.crossProduct(v3));
         try {
            v1.crossProduct(v3);
             fail("crossProduct() for parallel vectors does not throw an exception");
         } catch (Exception e) {}

    }

    /**
     * Test method for {@link Vector#lengthSquared()} (primitives.Vector)}
     */
    @Test
    public void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that square length of vector is proper
        assertTrue("ERROR: lengthSquared() wrong value",isZero(v1.lengthSquared() - 14));
    }


    /**
     * Test method for {@link Vector#length()} (primitives.Vector)}
     */
    @Test
    public void length() {
        Vector v1 = new Vector(0, 3, 4);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that length of vector is proper
        assertTrue("ERROR: length() wrong value",isZero(v1.length() - 5));
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void normalize() {
        Vector v = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that normalize() dose not create new Vector
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals("ERROR: normalize() function creates a new vector", vCopyNormalize, vCopy);

        //TC02: Test that the product of normalize() is a unit Vector
        assertTrue("ERROR: normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
    }
    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    public void normalized() {
        Vector v = new Vector(1,2,3);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that normalized() creates new Vector
        Vector u = v.normalized();
        assertNotEquals("ERROR: normalized() function does not create a new vector",v,u);
    }
}