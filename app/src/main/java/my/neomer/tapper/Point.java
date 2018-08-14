package my.neomer.tapper;

public class Point<T> {
    T x, y;

    Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    T getX() { return x; }
    T getY() { return y; }

    void setX(T value) { x = value; }
    void setY(T value) { y = value; }
}
