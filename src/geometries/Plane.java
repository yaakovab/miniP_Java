package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * class Plane represents a three-dimensional plane in the cartesean space
 * made up of Point3D class and Vector class
 */
public class Plane implements Geometry{
    /**
     * point q0 is associeated with the plane
     * Vector normal is the vector that orthogonal to the plane
     */

    private Point3D q0;
    private Vector normal;

    /**
     *
     * @param q0 is asocieated with the plane
     * @param normal is the vector that orthogonal to the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     *
     * @param a Point3D associated with the plane
     * @param b Point3D associated with the plane
     * @param c Point3D associated with the plane
     * generate the plane from these Points
     */
    public Plane(Point3D a,Point3D b,Point3D c) {
        Vector u = a.subtract(b);
        Vector v = a.subtract(c);
        Vector n = u.crossProduct(v);


        this.normal = n.normalize();
        this.q0 = a;
    }

    /**
     *
     * @param point Point3D on the plane
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /**
     *
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }

    public Point3D getQ0() {
        return q0;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0.toString() +
                ", normal=" + normal.toString() +
                '}';
    }

    /**
     * @param ray from the Camera
     * @return list of point that ray intersects with the Geometry shape
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(q0, plane.q0) &&
                Objects.equals(normal, plane.normal);
    }*/

}
