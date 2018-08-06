package my.neomer.tapper;

public class Vector {

    private double x, y;
    private double mLength;

    Vector() {
        x = 0;
        y = 0;
        mLength = 0;
    }

    Vector(double x, double y) {
        this.x = x;
        this.y = y;
        updateLength();
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

    public double getLength() {
        return mLength;
    }

    protected void updateLength() {
        mLength = Math.sqrt(x * x + y * y);
    }

    public void Add(Vector vector) {
        x += vector.x;
        y += vector.y;

        updateLength();
    }

    public void Multiply(double value) {
        x *= value;
        y *= value;

        updateLength();
    }

    public void Reflects(Vector normal) {
        
    }

    public String toString() {
        return String.format("(%f;%f)", x, y);
    }

}
