package primitives;


import java.util.Objects;

public class Point3D {
    /**
     * this class is to represent a 3-dimensional point in a crtesiean space
     */
      final Coordinate x;
      final Coordinate y;
      final Coordinate z;

    //region constructors
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double a, double b,double c) {
        Coordinate x = new Coordinate(a);
        Coordinate y = new Coordinate(b);
        Coordinate z = new Coordinate(c);


        this.x = x;
        this.y = y;
        this.z = z;
    }
    //endregion



    public static final Point3D ZERO = new Point3D(0.0,0.0,0.0);



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

    public Vector subtract(Point3D p){
        return new Vector(this.x.coord - p.x.coord,
                this.y.coord - p.y.coord, this.z.coord - p.z.coord);
    }

    public Point3D add(Vector v){
        return new Point3D(v.getHead().x.coord + this.x.coord,
                           v.getHead().y.coord + this.y.coord,
                           v.getHead().z.coord + this.z.coord);
    }

    public double distanceSquared(Point3D other){
        return  (this.x.coord - other.x.coord) * (this.x.coord - other.x.coord) +
                (this.y.coord - other.y.coord) * (this.y.coord - other.y.coord) +
                (this.z.coord - other.z.coord) * (this.z.coord - other.z.coord);
    }

    public double distance(Point3D other){
        return Math.sqrt(distanceSquared(other));
    }

}
