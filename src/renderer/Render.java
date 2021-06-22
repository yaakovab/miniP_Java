/**
 * @author Yaacov Abramowitz
 */
package renderer;

import elements.Camera;
import primitives.*;

import java.util.List;
import java.util.MissingResourceException;

public class Render {
    ImageWriter imageWriter = null;
    Camera camera = null;
    RayTracerBase rayTracerBase = null;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
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
            if (threadsCount == 0) {
                for (int i = 0; i < Ny; i++) {
                    for (int j = 0; j < Nx; j++) {
    //                  castRay(Nx, Ny, j, i);
                        Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i);
                        Color pixelColor = rayTracerBase.traceRay(ray);
                        Color singleRayColor = pixelColor; // store color of central ray for later use

                        // doing here the superSampling in case it is requested
                        if (mini_nX > 0 || mini_nY > 0) {
                            var sampleRaysList = camera.constructBeamOfRaysThroughPixel(
                                    Nx, Ny, j, i, mini_nX, mini_nY);
                            pixelColor = getWeighedColor(Nx, Ny, mini_nX, mini_nY, pixelColor, sampleRaysList);

                            // in case it's a pixel that has significant difference in its color
                            // then do a bigger resolution to get more accurate color
                            if (isSignificantDiffInColor(pixelColor, singleRayColor)) {
                                mini_nX = 9;
                                mini_nY = 9;
                                sampleRaysList = camera.constructBeamOfRaysThroughPixel(
                                        Nx, Ny, j, i, mini_nX, mini_nY);
                                pixelColor = getWeighedColor(Nx, Ny, mini_nX, mini_nY, singleRayColor, sampleRaysList);
                            }
                        }
                        imageWriter.writePixel(j, i, pixelColor);
                    }
                }
            } else {
                renderImageThreaded();
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException(e.getClassName() + " not initialized");
        }
    }


    private boolean isSignificantDiffInColor(Color pixelColor, Color singleRayColor) {
        return (Math.abs(pixelColor.getColor().getBlue() - singleRayColor.getColor().getBlue()) >= 25) &&
                (Math.abs(pixelColor.getColor().getRed() - singleRayColor.getColor().getRed()) >= 25) &&
                (Math.abs(pixelColor.getColor().getGreen() - singleRayColor.getColor().getGreen()) >= 25);
    }

    /**
     * printing grid in the background of scene
     *
     * @param interval between lines of grid
     * @param color    to paint lines of grid
     */
    public void printGrid(int interval, primitives.Color color) {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        // goes through all rows
        for (int i = 0; i < Ny; i += interval) {
            for (int j = 0; j < Nx; j++) {
                imageWriter.writePixel(j, i, color);
            }
        }
        // goes through all columns
        for (int j = 0; j < Nx; j += interval) {
            for (int i = 0; i < Ny; i++) {
                imageWriter.writePixel(j, i, color);
            }
        }

    }

    /**
     * Finally use with imageWriter to draw the image built
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        }

        imageWriter.writeToImage();

    }



    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
        Color pixelColor = rayTracerBase.traceRay(ray);
        Color singleRayColor = pixelColor;

        int mini_nX = imageWriter.getMini_nX();
        int mini_nY = imageWriter.getMini_nY();

        // doing here the superSampling in case it is requested
        if (mini_nX > 0 || mini_nY > 0) {
            var sampleRaysList = camera.constructBeamOfRaysThroughPixel(
                    nX, nY, col, row, mini_nX, mini_nY);
            pixelColor = getWeighedColor(nX, nY, mini_nX, mini_nY, pixelColor, sampleRaysList);

            // in case it's a pixel that has significant difference in its color
            // then do a bigger resolution to get more accurate color
            if (isSignificantDiffInColor(pixelColor, singleRayColor)) {
                mini_nX = 9;
                mini_nY = 9;
                sampleRaysList = camera.constructBeamOfRaysThroughPixel(
                        nX, nY, col, row, mini_nX, mini_nY);
                pixelColor = getWeighedColor(nX, nY, mini_nX, mini_nY, singleRayColor, sampleRaysList);
            }
        }
        imageWriter.writePixel(col, row, pixelColor);
    }



    private Color getWeighedColor(int nx, int ny, int mini_nX, int mini_nY, Color pixelColor, List<Ray> sampleRaysList) {
        // in case num of rows and columns is odd number the central
        // ray count twice so we check it and if it is it erases that color
        if (mini_nX % 2 == 1 && mini_nX % 2 == 1) {
            pixelColor = new Color(Color.BLACK);
        }

        for (Ray r : sampleRaysList) {
            pixelColor = pixelColor.add(rayTracerBase.traceRay(r));
        }

        double numOfMiniPixels = (mini_nX % 2 == 1 && mini_nY % 2 == 1 ?
                mini_nX * mini_nY : mini_nX * mini_nY + 1);

        //calculate the average color by dividing the final color by num of rays
        pixelColor = pixelColor.reduce(numOfMiniPixels);
        return pixelColor;
    }
    }
