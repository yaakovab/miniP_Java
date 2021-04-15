package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * this Interface stands for the intersection points that ray from camera intersects the Geometry shape
 */
public interface Intersectable {
    /**
     *
     * @param ray from the Camera
     * @return list of point that ray intersects with the Geometry shape
     */
    List<Point3D> findIntersections(Ray ray);

}
