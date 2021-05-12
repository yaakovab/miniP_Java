package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * this Interface stands for the intersection points that ray from Camera intersects the Geometry shape
 */
public interface Intersectable {

    /**
     * this PDS class is a helper class in order to reserve the intersected point in context
     * with the geometry shape to discern btn different colors of objects
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         * @param geometry Geometry shape that Ray intersects
         * @param point intersected Point in Geometry shape
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }
        //************************Admin********************
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                GeoPoint geoPoint = (GeoPoint) o;
                return Objects.equals(geometry, geoPoint.geometry) &&
                        Objects.equals(point, geoPoint.point);
            }

        }



    /**
     *  default implementation using func {@link #findGeoIntersections(Ray)}
     *  find intersections with ray
     * @param ray from the Camera
     * @return list of Points that ray intersects with the Geometry shape
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

        /**
         * find geo & point intersections with ray
         *
         * @param ray from Camera
         * @return list of GeoPoint that the ray intersects it
         */
    List<GeoPoint> findGeoIntersections(Ray ray);

}
