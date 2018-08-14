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

        //updateLength();
    }

    public void Multiply(double value) {
        x *= value;
        y *= value;

        //updateLength();
    }

    public void Reflects(Vector normal) {
        
    }

    public Vector Clone() {
        return new Vector(x, y);
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }

}
