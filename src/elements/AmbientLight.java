package elements;

import primitives.Color;

/**
 *
 * @author Yaacov
 * this class represent ambient light which is constant for each object at the scene
 *
 */
public class AmbientLight {
    /**
     * intensity of ambient light
     */
    final private Color intensity ;


    /**
     * constructor
     * @param Ia intensity color
     * @param Ka constant
     */

    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }



    /**
     * @return AmbientLight Color
     */
    public Color getIntensity() {
        return intensity;
    }


}