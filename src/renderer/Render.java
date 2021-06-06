/**
 * @author Yaacov Abramowitz
 */
package renderer;

import elements.Camera;
import primitives.*;
import java.util.MissingResourceException;

public class Render {

    ImageWriter imageWriter = null;
    Camera camera = null;
    RayTracerBase rayTracerBase = null;
    private boolean superSampling;

    /**
     * use builder pattern
     * @param superSampling boolean expression
     * @return this for chaining purpose
     */
    public Render setSuperSampling(boolean superSampling) {
        this.superSampling = superSampling;
        return this;
    }

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
            int mini_nX = imageWriter.getMini_nX();
            int mini_nY = imageWriter.getMini_nY();
            for (int i = 0; i < Ny; i++) {
                for (int j = 0; j < Nx; j++) {
                    Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i);
                    Color pixelColor = rayTracerBase.traceRay(ray);

                    // doing here the superSampling in case it is requested
                    if (superSampling) {
                        var sampleRaysList = camera.constructBeamOfRaysThroughPixel(
                                Nx, Ny, j, i, mini_nX, mini_nY);

                        // in case num of rows and columns is odd number the central
                        // ray count twice so we check it and if it is it erases that color
                        if(Nx % 2 == 1 && Ny % 2 == 1){
                            pixelColor = new Color(Color.BLACK);
                        }

                        for (Ray r : sampleRaysList) {
                          pixelColor =  pixelColor.add(rayTracerBase.traceRay(r));
                        }

                        double numOfMiniPixels = (Nx % 2 == 1 && Ny % 2 == 1 ?
                                mini_nX * mini_nY: mini_nX * mini_nY + 1);

                        pixelColor = pixelColor.reduce(numOfMiniPixels);
                    }

                    imageWriter.writePixel(j, i, pixelColor);
                }
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException(e.getClassName() + " not initialized");
        }
    }


    /**
     * printing grid in the background of scene
     * @param interval between lines of grid
     * @param color to paint lines of grid
     */
    public void printGrid(int interval, primitives.Color color) {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        // goes through all rows
        for (int i = 0; i < Ny; i+= interval) {
            for (int j = 0; j < Nx; j++) {
                imageWriter.writePixel(j, i, color);
            }
        }
        // goes through all columns
        for (int j = 0; j < Nx; j+= interval) {
            for (int i = 0; i < Ny; i++) {
                imageWriter.writePixel(j, i, color);
            }
        }

    }

    /**
     *  Finally use with imageWriter to draw the image built
     */
    public void writeToImage() {
        imageWriter.writeToImage();

    }

}