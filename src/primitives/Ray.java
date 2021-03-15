package primitives;

import java.util.Objects;

/**
 * this class represents a ray which consist of a vector
 * for direction and a point from which it starts
 */
public class Ray {

    private Point3D p0;
    private Vector dir;

    /**
     * @param dir gets normalized in the ctor and then assin to value
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        //vector assign is a normalized vector
        this.dir = dir.normalize();
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) &&
                Objects.equals(dir, ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }
}
