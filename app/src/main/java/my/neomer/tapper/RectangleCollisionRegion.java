package my.neomer.tapper;

import android.graphics.Rect;

public class RectangleCollisionRegion implements ICollisionRegion {

    private double mWidth, mHeight;
    private IPhysicsObject mPhysicsObject;

    RectangleCollisionRegion(IPhysicsObject owner, double width, double height) {
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
            Rect rect1 = GetMappedRect(coords1),
                    rect2 =  collisionRegion.GetMappedRect(coords2);

            return rect1.left <= rect2.right &&
                    rect1.right >= rect2.left &&
                    rect1.bottom <= rect2.top &&
                    rect1.top >= rect2.bottom;
        }

        return false;
    }

    @Override
    public Rect GetMappedRect(Coordinate point) {
        return new Rect(
                (int)mPhysicsObject.GetCoordinates().getX(),
                (int)mPhysicsObject.GetCoordinates().getY(),
                (int)(mPhysicsObject.GetCoordinates().getX() + getWidth()),
                (int)(mPhysicsObject.GetCoordinates().getY() + getHeight()));
    }
}
