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

    //  initialize attenuation factor to 1
    private static final double INITIAL_K = 1.0;
    //  number of times to calculate reflection and transparency
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    // this is the limit of allowing to k- attenuation to get smaller
    private static final double MIN_CALC_COLOR_K = 0.001;
    //  for wrongs in calculate ray start from surface of object
    //  we offset the point
    private static final double DELTA = 0.1;


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
     * @param intersection geoPoint contains the shape in which the ray intersects and the point of intersection
     * @return appropriate color for painting the view plane
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }
    /**
     * this function is responsible of painting in the appropriate color for a certain point of
     * intersection btn shape and camera
     * @param geoPoint geoPoint contains the shape in which the ray intersects and the point of intersection
     * @return appropriate color for painting the view plane
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor( geoPoint, ray,  MAX_CALC_COLOR_LEVEL,  INITIAL_K).add(scene.ambientlight.getIntensity());
    }

    /**
     * calculates additional effects on the color
     * @param intersection point and shape in which intersection
     *                     occured btn ray from camera and object
     * @param ray from Camera
     * @return Color
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
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
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;

    }

    /**
     * calculates the specular effect of light upon object
     * @param ks specular attenuation factor
     * @param l Vector from the source light to the object
     * @param n Vector normal to intersected point
     * @param v Vector from virtual Camera to object
     * @param nShininess of object
     * @param lightIntensity Color
     * @return appropriate specular component
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        // Calculating reflectance vector (r)
        Vector r = l.subtract(n.scale(alignZero(2 * l.dotProduct(n))));
        double vr = alignZero(v.dotProduct(r));
        double minusVr = vr * (-1);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));
    }

    /**
     * calculates the diffusive component of light shaded upon an object
     * @param kd diffusive attenuation factor
     * @param l Vector from the source light to the object
     * @param n Vector normal to intersected point
     * @param lightIntensity Color
     * @return appropriate diffusive component
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = alignZero(l.dotProduct(n));
        return lightIntensity.scale(kd * Math.abs(factor));
    }

    //private boolean unshaded(Vector l, Vector n, GeoPoint geopoint, LightSource lightSource)

    // like complex scens
    // that it shaded inside kubiya for example,
    // consider to add argument to represent the point of position of light, and
    // check wether it realy need to shade
    // may be shaded object is behind the light source
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections != null) {
            double lightDistance = light.getDistance(geopoint.point);
            for (GeoPoint intersection : intersections) {
                if (alignZero(intersection.point.distance(geopoint.point) - lightDistance) <= 0 && intersection.geometry.getMaterial().kt == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * calculate the idea of reflection and refraction
     *
     * @param geopoint
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        double kr = material.kr, kkr = k * kr;
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n,geopoint.point, ray);
            List<GeoPoint> geoList1 = scene.geometries.findGeoIntersections(reflectedRay);
            GeoPoint reflectedPoint = reflectedRay.getClosestGeoPoint(geoList1);
            if (geoList1 != null) {

                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
//
        double kt = material.kt, kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n,geopoint.point, ray);
            List<GeoPoint> geoList2 = scene.geometries.findGeoIntersections(refractedRay);
            GeoPoint refractedPoint = refractedRay.getClosestGeoPoint(geoList2);
            if (geoList2 != null) {

                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));

            }
        }

        return color;
    }

    /**
     * construct Refracted Ray
     *

     * @param inRay
     * @return the same direction original ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        return new Ray(point, inRay.getDir(), n);
    }

    /**
     * construct Reflected Ray
     *

     * @param ray
     * @return
     */
    private Ray constructReflectedRay(Vector n,Point3D point, Ray ray) {
        Vector v = ray.getDir();
        Vector r = null;
        try {
            r = v.subtract(n.scale(v.dotProduct(n)).scale(2)).normalized();
        } catch (Exception e) {
            return null;
        }
        return new Ray(point, r, n);
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geopoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kt;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

}