package elements;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this interface is specific for light source e.g. directional light, spot light etc. and unlike ambient light
 */
public interface LightSource {
    /**
     * @param p Point in object
     * @return the Color in that Point
     */
    public Color getIntensity(Point3D p);

    /**
     * @param p Point in object
     * @return Vector in direction of light
     */
    public Vector getL(Point3D p);

    /**
     * calculate distance between light source to point intersected
     * @param point of intersection
     * @return the distance
     */
    double getDistance(Point3D point);

}
