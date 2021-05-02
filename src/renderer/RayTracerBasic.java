
/**
 *
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

/**
 * @author chaim & michael
 *
 */
public class RayTracerBasic extends RayTracerBase  {

    public RayTracerBasic(Scene scene) {
        super(scene);

    }
    /**
     * paint every intersection point with appropriate color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersection = this.scene.geometries.findIntersections(ray);
        if (intersection != null) {
            Point3D close = ray.findClosestPoint(intersection);
            return calcColor(close);
        }
        return this.scene.background;
    }

    private Color calcColor(Point3D p) {
        return this.scene.ambientlight.getIntensity();
    }

}