package my.neomer.tapper;

public class Vector extends Point<Double> {

    private double mLength;

    Vector() {
        super(0.0, 0.0);
        mLength = 0;
    }

    Vector(double x, double y) {
        super(x, y);
        updateLength();
    }

    public double getLength() {
        return mLength;
    }

    protected void updateLength() {
        mLength = Math.sqrt(x * x + y * y);
    }

    public void Add(Vector vector) {
        x += vector.x;
        y += vector.y;
    }

    public Vector Multiply(double value) {
        return new Vector(x * value, y * value);
    }

    public Vector Clone() {
        return new Vector(x, y);
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }

    public void Clear() {
        x = 0.0;
        y = 0.0;
    }

}
