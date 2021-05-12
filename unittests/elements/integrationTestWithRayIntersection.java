package elements;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * this class is doing integration tests for Camera's methode constructRayThroughPixel
 *  and Intersactable's methode findIntersection
 *   It's validating that rays that goes through the view plane are indeed intersecting
 *   the geo shapes and that number of intersected points are as expected
 */
public class integrationTestWithRayIntersection {
    /**
     * Test method for rays through View Plane that intersect Sphere
     */
    @Test
    public void testIntersectionWithSphere(){
        // TC01: 2 intersection points only in a 3*3 view plane
        Sphere sphere = new Sphere(new Point3D(0,0,-3),1);
        Camera camera = new Camera(Point3D.ZERO,new Vector(0,0,-1),new Vector(0,1,0))
                .setDistance(1).setViewPlaneSize(3,3);
        int intersectionPoints = getIntersectionPoints(sphere,camera);
       assertEquals("bad Ray",2,intersectionPoints);

        // TC02: 18 intersection Points (big sphere)
        sphere = new Sphere(new Point3D(0,0,-2.5),2.5);
        camera = new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0))
                .setDistance(1).setViewPlaneSize(3,3);
        intersectionPoints = getIntersectionPoints(sphere,camera);
        assertEquals("bad Ray", 18,intersectionPoints);

        // TC03: 10 intersection Points (medium size Sphere)
        sphere = new Sphere(new Point3D(0,0,-2),2);
        camera = new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0))
                .setDistance(1).setViewPlaneSize(3,3);
        intersectionPoints = getIntersectionPoints(sphere,camera);
        assertEquals("bad Ray",10,intersectionPoints);

        // TC04: 9 intersection points (Camera is "inside" Sphere)
        sphere = new Sphere(new Point3D(0,0,-2.5),4);
        camera = new Camera(new Point3D(0,0,-1),new Vector(0,0,-1),new Vector(0,1,0))
                .setDistance(1).setViewPlaneSize(3,3);
        intersectionPoints = getIntersectionPoints(sphere,camera);
        assertEquals("bad Ray",9,intersectionPoints);

        // TC05: 0 intersection Points (Sphere is "behind" Camera)
        sphere = new Sphere(new Point3D(0,0,1),0.5);
        camera = new Camera(Point3D.ZERO,new Vector(0,0,-1),new Vector(0,1,0))
                .setDistance(1).setViewPlaneSize(3,3);
        intersectionPoints = getIntersectionPoints(sphere,camera);
        assertEquals("bad Ray",0,intersectionPoints);

        }

    /**
     * Test method for rays through View Plane that intersect Plane
     */
        @Test
        public void testIntersectionWithPlane(){
            // TC01: 9 intersection Points (Plane is orthogonal to View Plane
            Plane plane = new Plane(new Point3D(0,0,-2),new Vector(0,0,1));
             Camera camera = new Camera(new Point3D(0,0,-0.5),new Vector(0,0,-1),new Vector(0,1,0))
                     .setDistance(1).setViewPlaneSize(3,3);
            int intersectionPoints = getIntersectionPoints(plane, camera);
            assertEquals("bad Ray",9,intersectionPoints);

            // TC02: 9 intersection Points (Plane is not orthogonal to View Plane)
            plane = new Plane(new Point3D(0,0,-2),new Point3D(3,0,-4),new Point3D(-3,40,-5));
            camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setDistance(1).setViewPlaneSize(3,3);
            intersectionPoints = getIntersectionPoints(plane, camera);
            assertEquals("bad Ray",9,intersectionPoints);

            // TC03: 6 intersection Points (3 rays from bottom/up of matrix don't intersect Plane
            plane = new Plane(new Point3D(13,1,-0.5),new Point3D(0,4,-2),new Point3D(12,1,-1));
            camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setDistance(1).setViewPlaneSize(3,3);
            intersectionPoints = getIntersectionPoints(plane, camera);
            assertEquals("bad Ray",6,intersectionPoints);
        }

    /**
     * Test method for rays through View Plane that intersect Triangle
     */
        @Test
        public void testIntersectionWithTriangle(){
            // TC01: 1 intersection point
            Triangle triangle = new Triangle(new Point3D(1,-1,-2),
                    new Point3D(0,1,-2),new Point3D(-1,-1,-2));
            Camera camera = new Camera(new Point3D(0,0,-1),new Vector(0,0,-1),new Vector(0,1,0))
                    .setDistance(1).setViewPlaneSize(3,3);
            int intersectionPoint = getIntersectionPoints(triangle, camera);
            assertEquals("bad Ray",1,intersectionPoint);

            // TC02: Two intersection points
            triangle = new Triangle(new Point3D(1,-1,-2),
                    new Point3D(0,20,-2),new Point3D(-1,-1,-2));
            camera = new Camera(new Point3D(0,0,-1),new Vector(0,0,-1),new Vector(0,1,0))
                    .setDistance(1).setViewPlaneSize(3,3);
            intersectionPoint = getIntersectionPoints(triangle, camera);
            assertEquals("bad Ray",2,intersectionPoint);

        }

    /**
     * private utility function
     * @param geoShape any geometry shape
     * @param camera virtual camera that "sees" the scene
     * @return total number of intersected points through all pixels that intersect geoShape
     */
    private int getIntersectionPoints(Intersectable geoShape, Camera camera) {
        int intersectionPoints = 0;

        // Go through all pixels in View Plane matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // construct the Ray through PIXEL[i,j] and returns it
                var rayFromCameraThroughPixel = camera.constructRayThroughPixel(3, 3, i, j);
                // Ray computed above is set as parameter for findIntersection with geoShape
                // intersection Points are reserved temporarily in a list
                // in order to count number of points and validate it with expected result
                var listOfIntersectionPoints = geoShape.findIntersections(rayFromCameraThroughPixel);
                if (listOfIntersectionPoints != null) {
                    // Collecting the amounts of intersection Points
                    intersectionPoints += listOfIntersectionPoints.size();
                }
            }
        }
        return intersectionPoints;
    }


}
