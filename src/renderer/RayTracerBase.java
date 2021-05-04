package renderer;

import primitives.*;
import scene.Scene;

/**
 * @author Yaacov
 * this is an abstract class for painting a scene
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /** constructor
     * @param scene containing all that viewed through the Camera
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * this function using the technic of ray tracing in order to paint
     * every pixel in the view plane in the right color that ray that goes through
     * view plane to the scene
     * @param ray from camera
     * @return Color
     */
    public abstract Color traceRay (Ray ray);



}