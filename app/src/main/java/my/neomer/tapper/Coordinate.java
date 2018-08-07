package my.neomer.tapper;

public class Coordinate
{
    private double x, y;

    Coordinate() {
        x = 0; y = 0;
    }

    Coordinate(double x, double y) {
        this.x  = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void Add(Vector vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }
}
