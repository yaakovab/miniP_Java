package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

/**
 *  abstract class Geometry represents a general geometric shape with 1 method that
 * should be implemented in all its implantation and 1 member which is the color of each
 *  Geometry shape
 */
public abstract class Geometry implements Intersectable {

    protected Color emission = Color.BLACK;
    /**
     * @param point point on the shape
     * @return vector orthogonal to the point on the geometry
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * geter func
     * @return emission - unique color of each object
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * seter func
     * @param emission unique color of each object
     * @return this for chaining purposes
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

}