/**
 * @author Yaacov Abramowitz
 **/
package elements;
import primitives.Color;

/**
 * this class represent ambient light which is constant for each object at the scene
 */
public class AmbientLight extends Light{


    /**
     * constructor uses Light's constructor
     * @param Ia intensity color
     * @param Ka constant
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * default constructor - default color = Black
     * uses Light's constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}