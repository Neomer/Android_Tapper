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

    public double getLeft() { return mLeft; }

    public  double getTop() { return  mTop; }

    @Override
    public boolean checkIntersect(ICollisionElement collisionRegion) {
        if (collisionRegion == null)
        {
            return false;
        }

        if (collisionRegion instanceof RectangleCollisionElement)
        {
            return CollisionSolver.CheckRectToRectIntersection(this, (RectangleCollisionElement) collisionRegion);
        }
        else if (collisionRegion instanceof CircleCollisionElement)
        {
            return CollisionSolver.CheckRectToCircleIntersection(this, (CircleCollisionElement) collisionRegion);
        }

        return false;
    }

    public static Rect getMappedRect(RectangleCollisionElement collisionRegion, Coordinate point) {
        return new Rect(
                (int)(point.getX() + collisionRegion.getLeft()),
                (int)(point.getY() + collisionRegion.getTop()),
                (int)(point.getX() + collisionRegion.getLeft() + collisionRegion.getWidth()),
                (int)(point.getY() + collisionRegion.getTop() + collisionRegion.getHeight()));
    }

    @Override
    public void Draw(Canvas canvas) {
        Rect rect = getMappedRect(this, mPhysicsObject.GetCoordinates().Clone());
        canvas.drawRect(rect, mRegionPaint);
    }
}
