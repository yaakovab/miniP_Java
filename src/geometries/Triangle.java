package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Triangle represents a 2-dimensional triangle  in 3D Cartesian coordinate
 *  system
 */
public class Triangle extends Polygon{
    /**
     *
     * @param vertices list of points3D
     */
    public Triangle(Point3D... vertices) {
        /**
         * using father's constructor
         */
        super(vertices);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }
}
