package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * This class represents the Camera as one of the elements to build the Scene
 * @author yaacov
 */
public class Camera {
   final private Point3D p0;
   final private Vector vUP;
   final private Vector vTo;
   final private Vector vRight;
   private double width;
   private double height;
   private double distance;

    /**
     * Construct Camera in the 3D coordinate system
     * @param p0 position of Camera
     * @param vTo Vector in direction toward View Plane
     * @param vUP vector in up direction
     */
    public Camera(Point3D p0, Vector vTo, Vector vUP) {
        this.p0 = p0;
        this.vUP = vUP.normalized();
        this.vTo = vTo.normalized();

        if(!isZero(vUP.dotProduct(vTo))){
            throw new IllegalArgumentException("Vector vUp is not orthogonal to Vector vTo");
        }
        this.vRight = vTo.crossProduct(vUP).normalized();
    }

    /**
     * borrowing from builder pattern
     * @param width of View Plane
     * @param height of view plane
     * @return "this" Camera
     */
    public Camera setViewPlaneSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * borrowing from builder pattern
     * @param distance between Camera and Viee Plane
     * @return this Camera
     */
    public Camera setDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     * construct Ray through given Pixel[i,j]
     * @param nX number of columns in the View Plane matrix
     * @param nY number of rows in the View Plane matrix
     * @param j index of column
     * @param i index of row
     * @return Ray(Point3D, Vector direction) that goes through Pixel[i,j]
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        // PC = Image center
        Point3D pc = p0.add(vTo.scale(distance));
        // Pixel's height
        double rY = height / nY;
        // Pixel's Width
        double rX = width / nX;

        //Pij is for Point on a certain pixel
        Point3D pij = pc;

        //delta y
        double yI = - (i - (nY - 1) / 2d) * rY;
        // delta x
         double xJ = (j - (nX - 1) / 2d) * rX;

         // taking into account when Xj is zero in order to avoid scaling a vector in zero
         if(!isZero(xJ)){
             pij = pij.add(vRight.scale(xJ));
         }
        // taking into account when Yi is zero in order to avoid scaling a vector in zero
         if(!isZero(yI)){
             pij = pij.add(vUP.scale(yI));
         }

         return new Ray(p0,pij.subtract(p0));
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getVUP() {
        return vUP;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVRight() {
        return vRight;
    }
}
