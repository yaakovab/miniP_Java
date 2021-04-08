package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,1),2);

        assertEquals("Bad normal to triangle"
                , new Vector(0,0,1),sphere.getNormal(new Point3D(0,0,3)));
    }
}