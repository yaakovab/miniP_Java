package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.*;


/**
 * This class represents a collection of geometric shapes
 */
public class Geometries implements Intersectable{

   private List<Intersectable> intersectables;

    /**
     * create empty LinkedList
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * @param list of shapes
     * using Utility func add to assign list to member intersectables
     */
    public Geometries(Intersectable... list){
        intersectables = new LinkedList<>();
        add(list);
    }

    /**
     * Utility func
     * @param list of shapes
     */
    public void add(Intersectable... list) {
        addAll(intersectables, list);
    }

    /**
     * @param ray from the Camera
     * @return list of point that ray intersects with the Geometric Shapes
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable geo : intersectables) {
            List<Point3D> geoPoints = geo.findIntersections(ray);
            if(geoPoints != null){
                if(result == null){
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }
}
