package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 *  interface Geometry represents a general geometric shape with 1 method that
 * should be implemented in all its implantation
 */
public interface Geometry {
    /**
     * @param point point on the shape
     * @return vector orthogonal to the point in the geometry
     */
    public Vector getNormal(Point3D point);
}