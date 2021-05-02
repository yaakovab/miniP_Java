
/**
 *
 */
package renderer;

import primitives.*;
import scene.Scene;

/**
 * @author chaim & michael
 * abstract class for panting a scene
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /** constructor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * get ray and return the color needed
     * @param ray
     * @return
     */
    public abstract Color traceRay (Ray ray);



}