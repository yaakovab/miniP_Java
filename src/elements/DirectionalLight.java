package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class represents directional that comes from far away
 * there is NO attenuation for this light which means that intensity
 * is equal no matter where the point is
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * constructor
     * @param intensity of Color
     * @param direction of Light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * @param p Point in object
     * @return the Color in that Point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * @param p Point in object
     * @return normalize Vector direction
     */
    @Override
    public Vector getL(Point3D p) {
        Vector l = direction.normalized();
        return l;
    }
}
