package geometries;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import javax.management.ListenerNotFoundException;
import java.util.List;

/**
 * class Sphere represents a sphere in 3D dimensional cartesian system
 */

public class Sphere implements Geometry{
    /**
     * center is a Point3D for center of the sphere
     * radius
     */
    private Point3D center;
    private double radius;

    /**
     *
     * @param center is a Point3D for center of the sphere
     * @param radius radius
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center.toString() +
                ", radius=" + radius +
                '}';
    }

    /**
     *
     * @param point Point3D on the sphere
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
       Vector vectorNormal = point.subtract(center);
        return vectorNormal.normalize();
    }

    /**
     * @param ray from the Camera
     * @return list of point that ray intersects with the Geometry shape
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector dir = ray.getDir();

        //Case ray starts at center
       if(center.equals(ray.getP0())){

         Point3D p = ray.getP0().add(dir.scale(radius));
           return List.of(p);
       }
        //u = O -p0
      Vector u = center.subtract(ray.getP0());

      //tm = uv  \** v is direction of ray
        double tm = alignZero(u.dotProduct(dir));

      //d = sqrt((|u|^2 - tm62))
      double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
      if(d >= radius){
          return null;
      }
      //th = sqrt(r^2 - d^2)
      double th = alignZero(Math.sqrt(radius * radius - d * d));
      double t1 = alignZero(tm + th);
      double t2 =  alignZero(tm - th);
      Point3D p1 = null;
       Point3D p2 = null;
      if(t1 > 0){
         p1 = ray.getP0().add(dir.scale(t1));
      }
      if(t2 > 0){
          p2 = ray.getP0().add(dir.scale(t2));
      }
      if(t1 > 0 && t2 > 0){
        return List.of(p1,p2);
      }
      if(t1 > 0){
          return List.of(p1);
      }
      if(t2 > 0){
          return List.of(p2);
      }
        return null;
    }
}
