package my.neomer.tapper;

public class Coordinate extends Point<Double>
{
    public Coordinate() {
        super(0.0, 0.0);
    }

    public Coordinate(double x, double y) {
        super(x, y);
    }

    public void Add(Vector vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public void Add(Coordinate coordinate) {
        x += coordinate.getX();
        y += coordinate.getY();
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }

    public  Coordinate Clone() {
        return new Coordinate(x, y);
    }

}
