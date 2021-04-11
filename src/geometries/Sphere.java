package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class Sphere represents a sphere in 3D dimensional cartesian system
 */

public class Sphere implements Geometry{
    /**
     * center is a Point3D for center of the sphere
     * radius
     */
    private Point3D center;
    private double radius;

    /**
     *
     * @param center is a Point3D for center of the sphere
     * @param radius radius
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center.toString() +
                ", radius=" + radius +
                '}';
    }

    /**
     *
     * @param point Point3D on the sphere
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
       Vector vectorNormal = point.subtract(center);
        return vectorNormal.normalize();
    }
}
