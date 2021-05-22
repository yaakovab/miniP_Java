package elements;
import primitives.Color;

/**
 * this is an abstract super-class for all different kinds of light that can exits in a scene
 * (ambient, directoinal, etc)
 * it has no specifier
 */
abstract class Light {

    protected Color intensity;

    /**
     * constructor
     * @param intensity of Color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
