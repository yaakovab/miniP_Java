package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

/**
 * this class represents a ray which consist of a vector
 * for direction and a point from which it starts
 * and based on class Vector and Point3D
 */
public class Ray {

    private Point3D p0;
    private Vector dir;

    /**
     * @param p0 Point3D from which the ray starts
     * @param dir vector for direction of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        //vector dir assigned a normalized vector
        this.dir = dir.normalize();
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }


    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) &&
                Objects.equals(dir, ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }

    public Point3D getPoint(double t) {
        return getP0().add(getDir().scale(t));
    }

    /**
     * find the closet point to p0 and return it
     * @param intersections list of intersection points
     * @return closet point to p0
     */
    public Point3D findClosestPoint (List<Point3D> intersections)
    {
        Point3D result = null;
        double closeDistance = Double.MAX_VALUE;

        // first check if the list is not empty
        if (intersections != null) {
            for (Point3D p:intersections) {
                double temp = this.p0.distanceSquared(p);
                if (temp < closeDistance) {
                    closeDistance = temp;
                    result = p;
                }


            }
        }

        return result;
    }

    /**
     * find closest geo point to ray intersects it
     * @param intersections list of GeoPoints that ray from camera intersects it
     * @return closest GeoPoint to ray from camera
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> intersections){
       GeoPoint geoPoint = null;
        double closeDistance = Double.MAX_VALUE;

        // first check if the list is not empty
        if (intersections != null) {
            for (GeoPoint gp:intersections) {
                double temp = this.p0.distanceSquared(gp.point);
                if (temp < closeDistance) {
                    closeDistance = temp;
                    geoPoint = gp;
                }

            }
        }
        return geoPoint;
    }
}
