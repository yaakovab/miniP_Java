package primitives;

import java.util.Objects;

public class Vector {
    /**
     * this class represents vector in a 3-dimensional space
     */
    private Point3D head;

    //region Constructors
    public Vector(Coordinate x ,Coordinate y ,Coordinate z){
        head = new Point3D(x,y,z);

        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Zero vector not accepted here");
    }

    public Vector(Point3D head) {
        this.head = head;

        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Zero vector not accepted here");
    }

    public Vector(double a, double b, double c){
        head = new Point3D(a,b,c);

        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Zero vector not accepted here");
    }

    //endregion


    public Point3D getHead() {
        return head;
    }

    public double getCoordX(){
        return head.x.coord;
    }

    public double getCoordY(){
        return head.y.coord;
    }

    public double getCoordZ(){
        return head.z.coord;
    }

    public Vector subtract(Vector other){
        //using subtract method of Point3D's class
      return this.head.subtract(other.head);
    }

    public Vector add(Vector other){
        return new Vector(this.head.add(other));
    }

    public Vector scale(double scalar){
        return new Vector(getCoordX() * scalar,
                getCoordY() * scalar,
                getCoordZ() * scalar);
    }

    public double dotProduct(Vector other){
        return (getCoordX() * other.getCoordX() +
                getCoordY() * other.getCoordY() +
                getCoordZ() * other.getCoordZ());
    }

    /**
     *
     * @return new Vector which is orthogonal to both original Vectors
     */
    public Vector crossProduct(Vector other){
        //use the formula for cross product btn 2 vectors
        return new Vector(getCoordY() * other.getCoordZ() - getCoordZ() * other.getCoordY(),
                getCoordZ() * other.getCoordX() - getCoordX() * other.getCoordZ(),
                getCoordX() * other.getCoordY() - getCoordY() * other.getCoordX());
    }

    /**
     *
     * @return the length of the Vector - squared - (x^2 + y^2 + z^2)
     */
    public double lengthSquared(){
        return (getCoordX() * getCoordX() +
                getCoordY() * getCoordY() +
                getCoordZ() * getCoordZ());
    }

    /**
     *
     * @return the length of the Vector - sqrt(x^2 + y^2 + z^2)
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * @return *this* Vector normalized i.e *this* Vector is being changed
     */
    public Vector normalize(){
        double lengthOfVec = length();

        //initiate new point with coordinates of vector that were normalized
        //used later to assign to the head of *this* Vector
        Point3D p = new Point3D(getCoordX() / lengthOfVec,
                getCoordY() / lengthOfVec,
                getCoordZ() / lengthOfVec);
        head = p;
        return this; //Allows concatenation
    }

    /**
     * @return new Vector which was normalised from *this* vector
     */
    public Vector normalized(){
        //store original value of head bc it's about to change
       Point3D p = head;
       //assigning normalize vector to a new one using normalize method
       Vector v = normalize();
       //assign back the original value of heat to *this* vector
       head = p;

       return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(head, vector.head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head.toString() +
                '}';
    }
}
