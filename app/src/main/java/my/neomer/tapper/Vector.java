package my.neomer.tapper;

public class Vector {

    private float x, y;

    Vector() {
        x = 0;
        y = 0;
    }

    Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
