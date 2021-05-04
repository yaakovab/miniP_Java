package scene;
import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * @author Yaacov
 * Passive Data Structure class that hold the data of scene object
 */
public class Scene {

    public final String sceneName;
    public Color background = Color.BLACK ;
    public AmbientLight ambientlight = new AmbientLight(Color.BLACK , 1d);
    public Geometries geometries;


    public Scene(String sceneName) {
        this.sceneName = sceneName;
        this.geometries = new Geometries();
    }


    /**
     * @param background sets the background
     * @return this for chaining purposes
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }


    /**
     * @param ambientLight sets the AmbientLight
     * @return this for chaining purposes
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientlight = ambientLight;
        return this;
    }


    /**
     * @param geometries sets the geometries
     * @return this for chaining purposes
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}