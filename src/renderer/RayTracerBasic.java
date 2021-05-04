package renderer;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
/**
 * @author Yaacov
 *
 */

/**
 * this class is responsible for painting each pixel in view plan using the
 * technic of ray tracing
 */
public class RayTracerBasic extends RayTracerBase  {


    /** constructor
     * @param scene containing all that viewed through the Camera
     */
    public RayTracerBasic(Scene scene) {
        super(scene);

    }
    /**
     * this function using the technic of ray tracing in order to paint
     * every pixel in the view plane in the right color that ray that goes through
     * view plane to the scene
     * @param ray from camera
     * @return if ray intersects with anything from scene it returns the right color
     * for it -using calcColor-, if not, returns color of background of scene
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersection = scene.geometries.findGeoIntersections(ray);
        if (intersection != null) {
            GeoPoint close = ray.getClosestGeoPoint(intersection);
            return calcColor(close);
        }
        return scene.background;
    }

    /**
     * this function is responsible of painting in the appropriate color for a certain point of
     * intersection btn shape and camera
     * @param gp geoPoint contains the shape in which the ray intersects and the point of intersection
     * @return appropriate color for painting the view plane
     */
    private Color calcColor(GeoPoint gp) {
        return scene.ambientlight.getIntensity().add(gp.geometry.getEmission());
    }

}