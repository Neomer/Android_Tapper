package my.neomer.tapper;

public class Point<T> {
    protected T x, y;

    Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() { return x; }
    public T getY() { return y; }

    public void setX(T value) { x = value; }
    public void setY(T value) { y = value; }
}
