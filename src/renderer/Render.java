/**
 * @author Yaacov Abramowitz
 *
 */
package renderer;
import elements.Camera;
import primitives.*;
import scene.Scene;
import java.util.MissingResourceException;

public class Render {

    ImageWriter imageWriter = null;
    Camera camera = null;
    RayTracerBase rayTracerBase = null;


    /**
     * @param imageWriter the imageWriter to set
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * @param camera the camera to set
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;

    }
    /**
     * @param rayTracerBase the rayTracerBase to set
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;

    }
    /**
     * @return the imageWriter
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }
//    /**
//     * @return the scene
//     */
//  //  public Scene getScene() {
//   //     return scene;
//  //  }
    /**
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }
    /**
     * @return the rayTracerBase
     */
    public RayTracerBase getRayTracerBase() {
        return rayTracerBase;
    }





    /**
     * use with all element to write the details into "one box"
     */


    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getCanonicalName(), "");
            }
            if (camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getCanonicalName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getCanonicalName(), "");
            }
            // if all right ... rendering the image
            int Nx = imageWriter.getNx();
            int Ny = imageWriter.getNy();
            for (int i = 0; i < Ny; i++) {
                for (int j = 0; j < Nx; j++) {

                    Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i);
                    Color pixelColor = rayTracerBase.traceRay(ray);
                    imageWriter.writePixel(j,i,pixelColor);
                }
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException( e.getClassName() + " not initialized");
        }
    }


    /**
     * printing grid for work with it
     * @param interval
     * @param color
     */
    public void printGrid(int interval, primitives.Color color)
    {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }


    }
    /**
     *  Finally use with imageWriter to draw the image builded
     */
    public void writeToImage() {
        imageWriter.writeToImage();

    }

}