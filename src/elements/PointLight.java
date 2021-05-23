package elements;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

public class PointLight extends Light implements LightSource{

    private Point3D position; // where the source light is

    // attenuation factors
    // light is changing with distance
    private double kC;
    private double kL;
    private double kQ;

    //**************setters************//
    /**
     * setter using builder pattern
     * @param kC attenuation factor
     * @return this for chaining purpose
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter using builder pattern
     * @param kL attenuation factor
     * @return this for chaining purpose
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter using builder pattern
     * @param kq attenuation factor
     * @return this for chaining purpose
     */
    public PointLight setKq(double kq) {
        this.kQ = kq;
        return this;
    }


    /**
     * constructor
     * @param intensity of Color
     * @param position of point light
     * default values for kC, kL, kD (attenuation factors)
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * @param p Point in object
     * @return the Color in that Point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance = alignZero( p.distance(position));
        Color iL = getIntensity().scale(1 / (kC + kL * distance + kQ * distance * distance));
        return iL;
    }



    /**
     * @param p Point in object
     * @return Vector from that point to the point where the source light is
     */
    @Override
    public Vector getL(Point3D p) {
        Vector dirOfLight = p.subtract(position).normalized();
        return dirOfLight;
    }

    /**
     * calculate distance between light source to point intersected
     * @param point of intersection
     * @return the distance
     */
    @Override
    public double getDistance(Point3D point) {
        return position.distance(point);
    }
}
