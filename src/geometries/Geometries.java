package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


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
     * @param list of 3D Points
     * using Utility func add to assign list to member intersectables
     */
    public Geometries(Intersectable... list){
        add(list);
    }

    /**
     * Utility func
     * @param list of 3D Points
     */
    private void add(Intersectable... list) {
        Collections.addAll(intersectables, list);
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
