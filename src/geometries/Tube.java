package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class tube represents an infinite tube in 3D cartesian system
 */
public class Tube implements Geometry{
    /**
     * axis ray and radius is what makes up the tube
     */
    protected Ray axisRay;
    protected double radius;

    /**
     *
     * @param axisRay Tube in the direction of the Ray
     * @param radius radius of Tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
    /**
     *
     * @param point Point3D on the Tube
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector direction = axisRay.getDir();
        Point3D p_0 = axisRay.getP0();

        //t = v(p-p0)
        Vector p_p0 = point.subtract(p_0);
        double s = direction.dotProduct(p_p0);

        Point3D o = p_0;
      //O = p0 + tv
        if(s != 0) {
            Vector tv = direction.scale(s);
            o = p_0.add(tv);
        }

        Vector vn = point.subtract(o);
        return vn.normalize();
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                '}';
    }
}
