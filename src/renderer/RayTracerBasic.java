/**
 * @author Yaacov
 */

package renderer;
import java.util.List;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * this class is responsible for painting each pixel in view plan using the
 * technic of ray tracing
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     *  constructor
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
            return calcColor(close, ray);
        }
        return scene.background;
    }

    /**
     * this function is responsible of painting in the appropriate color for a certain point of
     * intersection btn shape and camera
     * @param gp geoPoint contains the shape in which the ray intersects and the point of intersection
     * @return appropriate color for painting the view plane
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientlight.getIntensity()
                .add(gp.geometry.getEmission()
                        .add(calcLocalEffects(gp, ray)));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        // v is the ray from camera and is normalized
        Vector v = ray.getDir().normalized();
        //
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;

    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        // -2 * (l n) n
        double ab = alignZero((2) * l.dotProduct(n));
        Vector a = n.scale(ab);
        // l - a
        Vector r = l.subtract(a);
        double vr = alignZero(v.dotProduct(r));
        double minusVr = vr * (-1);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = alignZero(l.dotProduct(n));
        return lightIntensity.scale(kd * Math.abs(factor));
    }

}