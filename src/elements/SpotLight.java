package elements;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.alignZero;

/**
 * this class represents spot light source that has a direction (e.g. luxo lamp)
 */
public class SpotLight extends PointLight{

    private Vector direction;

    /**
     * constructor
     * @param intensity of Color
     * @param position  of point light
     * @param direction where most intensity of light goes there
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    /**
     * @param p Point in object
     * @return the Color in that Point
     */
    @Override
    public Color getIntensity(Point3D p) {
        Vector dirNorm = direction.normalized();
        double vn = alignZero(dirNorm.dotProduct(getL(p)));
        return super.getIntensity(p).scale(Math.max(0, vn));
    }

    /**
     * @param p Point in object
     * @return Vector from that point to the point where the source light is
     */
    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }
}
