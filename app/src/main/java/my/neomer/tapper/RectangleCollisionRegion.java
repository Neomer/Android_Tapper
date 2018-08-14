package my.neomer.tapper;

import android.graphics.Rect;

public class RectangleCollisionRegion implements ICollisionRegion {

    private double mWidth, mHeight, mLeft, mTop;
    private IPhysicsObject mPhysicsObject;

    RectangleCollisionRegion(IPhysicsObject owner, double width, double height) {
        mLeft = 0;
        mTop = 0;
        mWidth = width;
        mHeight = height;
        mPhysicsObject = owner;
    }

    RectangleCollisionRegion(IPhysicsObject owner, double left, double top, double width, double height) {
        mLeft = left;
        mTop = top;
        mWidth = width;
        mHeight = height;
        mPhysicsObject = owner;
    }

    public IPhysicsObject getPhysicsObject() {
        return mPhysicsObject;
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

    public boolean checkIntersect(ICollisionRegion collisionRegion) {
        if (collisionRegion == null)
        {
            return false;
        }

        if (collisionRegion instanceof RectangleCollisionRegion)
        {
            RectangleCollisionRegion rectRegion = (RectangleCollisionRegion) collisionRegion;
            Coordinate coords1 = mPhysicsObject.GetCoordinates().Clone(),
                        coords2 = ((RectangleCollisionRegion) collisionRegion).getPhysicsObject().GetCoordinates().Clone();

            Rect rect1 = getMappedRect(this, coords1),
                    rect2 = getMappedRect(collisionRegion, coords2);

            return rect1.intersect(rect2);

            /* manual algorithm
            return rect1.left <= rect2.right &&
                    rect1.right >= rect2.left &&
                    rect1.bottom <= rect2.top &&
                    rect1.top >= rect2.bottom;
            */
        }

        return false;
    }

    public Rect getMappedRect(ICollisionRegion collisionRegion, Coordinate point) {
        return new Rect(
                (int)(mPhysicsObject.GetCoordinates().getX() + mLeft),
                (int)(mPhysicsObject.GetCoordinates().getY() + mTop),
                (int)(mPhysicsObject.GetCoordinates().getX() + mLeft + getWidth()),
                (int)(mPhysicsObject.GetCoordinates().getY() + mTop + getHeight()));
    }
}
