package my.neomer.tapper;

public class RectangleCollisionRegion implements ICollisionRegion {

    private double mWidth, mHeight;

    RectangleCollisionRegion(double width, double height) {
        mWidth = width;
        mHeight = height;
    }

    public double getWidth() {
        return mWidth;
    }

    public void setWidth(double width) {
        this.mWidth = width;
    }

    public double getHeight() {
        return mHeight;
    }

    public void setHeight(double height) {
        this.mHeight = height;
    }
}
