package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class Cylinder extends class Tube and is a finite Tube in 3D cartesian system
 */
public class Cylinder extends Tube{
    /**
     * height of the Cylinder
     */
    private double height;

    /**
     *
     * @param axisRay Tube in the direction of the Ray
     * @param radius radius of Tube
     * @param height height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    /**
     *
     * @param point Point3D on the Cylinder
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                '}';
    }
}
