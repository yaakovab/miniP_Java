package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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


    /**
     * @param ray from Camera
     * @return list of Points being intersected with ray in context with their location
     * on a geometry shape
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<Point3D> intersectedPoint = getIntersections(ray);
        if(intersectedPoint != null){
            GeoPoint geoPoint = new GeoPoint(this, intersectedPoint.get(0));
            return List.of(geoPoint);
        }
        return null;
    }


    /**
     * utility func for {@link #findGeoIntersections(Ray)}
     * @param ray from the Camera
     * @return list of point that ray intersects with the Geometry shape
     */

    private List<Point3D> getIntersections(Ray ray) {

        // if ray doesn't intersect the plan consist in triangle return null
        if (this.plane.findIntersections(ray) == null) {
            return null;
        }
        Vector v = ray.getDir();

        Point3D p1 = vertices.get(0);
        Point3D p2 = vertices.get(1);
        Point3D p3 = vertices.get(2);

        // otherwise check if ray intersect the triangle
        Vector v1 = p1.subtract(ray.getP0());
        Vector v2 = p2.subtract(ray.getP0());
        Vector v3 = p3.subtract(ray.getP0());

        Vector N1 = v1.crossProduct(v2);
        Vector N2 = v2.crossProduct(v3);
        Vector N3 = v3.crossProduct(v1);

        List<Point3D> ret = null;
        if ((v.dotProduct(N1) > 0 && v.dotProduct(N2) > 0 && v.dotProduct(N3) > 0) ||
                (v.dotProduct(N1) < 0 && v.dotProduct(N2) < 0 && v.dotProduct(N3) < 0)) {
            ret = this.plane.findIntersections(ray);


        }
        return ret;
    }

    public List<Point3D> findIntersections(Ray ray){
        List<Point3D> res = getIntersections(ray);
        return res;
    }
}
