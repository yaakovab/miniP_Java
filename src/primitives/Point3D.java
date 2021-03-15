package primitives;
import java.util.Objects;

/**
 *  @author
 * this class is to represent a 3-dimensional point in a cartesiean space
 * and based on the Coordinate class
 */
public class Point3D {
    /**
     * Coordinates values, intentionally "package-friendly" due to performance
     * constraints
     */
      final Coordinate x;
      final Coordinate y;
      final Coordinate z;

    //region constructors

    /**
     * constructor recieving 3 Coordinates
     *
     * @param x Coordinate x-axis
     * @param y Coordinate y-axis
     * @param z Coordinate z-axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * constructor receiving 3 values for Coordinates values
     *
     * @param a for x-axis
     * @param b for y-axis
     * @param c for z-axis
     */
    public Point3D(double a, double b,double c) {
        Coordinate x = new Coordinate(a);
        Coordinate y = new Coordinate(b);
        Coordinate z = new Coordinate(c);


        this.x = x;
        this.y = y;
        this.z = z;
    }
    //endregion


    /**
     * constant Point3D of value (0,0,0)
     */
    public static final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    /**
     *
     * @param point3D another point
     * @return Vector received from subtracting point from another point
     */
    public Vector subtract(Point3D point3D){
        return new Vector(this.x.coord - point3D.x.coord,
                this.y.coord - point3D.y.coord, this.z.coord - point3D.z.coord);
    }

    /**
     *
     * @param vector
     * @return new point received from adding vector to point
     */
    public Point3D add(Vector vector){
        return new Point3D(vector.getHead().x.coord + this.x.coord,
                           vector.getHead().y.coord + this.y.coord,
                           vector.getHead().z.coord + this.z.coord);
    }

    /**
     *
     * @param other point3D
     * @return distance squared btn the 2 points
     */
    public double distanceSquared(Point3D other){
        return  (this.x.coord - other.x.coord) * (this.x.coord - other.x.coord) +
                (this.y.coord - other.y.coord) * (this.y.coord - other.y.coord) +
                (this.z.coord - other.z.coord) * (this.z.coord - other.z.coord);
    }

    /**
     *
     * @param other point3D
     * @return distance btn 2 points
     */
    public double distance(Point3D other){
        return Math.sqrt(distanceSquared(other));
    }

    /*************** Admin *****************/
    //region equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(x, point3D.x) &&
                Objects.equals(y, point3D.y) &&
                Objects.equals(z, point3D.z);
    }
    //endregion


    //region toString
    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ' ';
    }
    //endregion
}
