package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{
    protected Point3D q0;
    protected Vector normal;

    @Override
    public Vector geNormal(Point3D point) {
        return null;
    }
}
