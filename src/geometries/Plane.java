package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Plane represents a three-dimensional plane in the cartesean space
 * made up of Point3D class and Vector class
 */
public class Plane extends Geometry {
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
     * @param ray from Camera
     * @return list of Points being intersected with ray in context with their location
     * on a geometry shape
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray){
        List<Point3D> intersectedPoint = getIntersections(ray);
        if(intersectedPoint != null){
            GeoPoint geoPoint = new GeoPoint(this, intersectedPoint.get(0));
            return List.of(geoPoint);
        }
        return null;
    }

    /**
     * private util func
     * @param ray from the Camera
     * @return list of Points that ray intersects with the Geometry shape
     */
    private List<Point3D> getIntersections(Ray ray) {
        Point3D P0 = ray.getP0();   //040
        Vector v = ray.getDir();    //010

        Vector n = normal;  //010

        if(q0.equals(P0)){
            return  null;
        }

        Vector P0_Q0 = q0.subtract(P0);  //200 -040 = 2 -4 0

        double denominator = alignZero(n.dotProduct(P0_Q0)); //010 . 2 -4 0 = -4

        //
        if (isZero(denominator)){
            return null;
        }

        //nominator
        double nv = alignZero(n.dotProduct(v));  //010 . 010 = 1

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(denominator / nv); //-4

        if (t <= 0){
            return  null;
        }
        Point3D P = ray.getPoint(t);

        return List.of(P);
    }


}
