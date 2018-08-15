package my.neomer.tapper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectangleCollisionElement implements ICollisionElement {

    private double mWidth, mHeight, mLeft, mTop;
    private IPhysicsObject mPhysicsObject;
    private Paint mRegionPaint;

    public RectangleCollisionElement(IPhysicsObject owner, double width, double height) {
        mLeft = 0;
        mTop = 0;
        mWidth = width;
        mHeight = height;
        mPhysicsObject = owner;

        mRegionPaint = new Paint();
        mRegionPaint.setColor(Color.argb(80, 127, 127, 255));

    }

    public RectangleCollisionElement(IPhysicsObject owner, double left, double top, double width, double height) {
        mLeft = left;
        mTop = top;
        mWidth = width;
        mHeight = height;
        mPhysicsObject = owner;

        mRegionPaint = new Paint();
        mRegionPaint.setColor(Color.argb(80, 127, 127, 255));
    }

    @Override
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

    @Override
    public boolean checkIntersect(ICollisionElement collisionRegion) {
        if (collisionRegion == null)
        {
            return false;
        }

        if (collisionRegion instanceof RectangleCollisionElement)
        {
            RectangleCollisionElement rectRegion = (RectangleCollisionElement) collisionRegion;
            Coordinate coords1 = mPhysicsObject.GetCoordinates().Clone(),
                        coords2 = collisionRegion.getPhysicsObject().GetCoordinates().Clone();

            Rect rect1 = getMappedRect(this, coords1),
                    rect2 = getMappedRect(rectRegion, coords2);

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

    private Rect getMappedRect(RectangleCollisionElement collisionRegion, Coordinate point) {
        return new Rect(
                (int)(point.getX() + mLeft),
                (int)(point.getY() + mTop),
                (int)(point.getX() + mLeft + collisionRegion.getWidth()),
                (int)(point.getY() + mTop + collisionRegion.getHeight()));
    }

    @Override
    public void Draw(Canvas canvas) {
        Rect rect = getMappedRect(this, mPhysicsObject.GetCoordinates().Clone());
        canvas.drawRect(rect, mRegionPaint);
    }
}
