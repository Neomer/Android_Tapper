package my.neomer.tapper;

public class Coordinate extends Point<Double>
{
    Coordinate() {
        super(0.0, 0.0);
    }

    Coordinate(double x, double y) {
        super(x, y);
    }

    public void Add(Vector vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }

    public  Coordinate Clone() {
        return new Coordinate(x, y);
    }

}
