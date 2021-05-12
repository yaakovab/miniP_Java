package elements;

import primitives.Color;

/**
 *
 * @author Yaacov
 * this class represent ambient light which is constant for each object at the scene
 *
 */
public class AmbientLight extends Light{


    /**
     * constructor
     * @param Ia intensity color
     * @param Ka constant
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }




}