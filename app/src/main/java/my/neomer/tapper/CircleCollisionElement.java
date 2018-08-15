package my.neomer.tapper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.security.cert.CertificateNotYetValidException;

public class CircleCollisionElement implements ICollisionElement {

    IPhysicsObject mPhysicsObject;
    Coordinate mCenter;
    double mRaduis, mSquareRaduis;

    Paint mDefaultPaint;

    public  CircleCollisionElement(IPhysicsObject physicsObject, Coordinate center, double raduis) {
        mPhysicsObject = physicsObject;
        mCenter = center;
        mRaduis = raduis;
        mSquareRaduis = raduis * raduis;

        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.argb(80, 127, 127, 255));
    }

    public double getRaduis() {
        return mRaduis;
    }

    public double getSquareRaduis() {
        return mSquareRaduis;
    }

    public Coordinate getCenter() {
        return mCenter;
    }

    @Override
    public boolean checkIntersect(ICollisionElement collisionElement) {
        if (collisionElement == null)
        {
            return false;
        }

        if (collisionElement instanceof RectangleCollisionElement)
        {
            return CollisionSolver.CheckRectToCircleIntersection((RectangleCollisionElement) collisionElement, this);
        }
        else if (collisionElement instanceof CircleCollisionElement)
        {
            return CollisionSolver.CheckCircleToCircleIntersection(this, (CircleCollisionElement) collisionElement);
        }

        return false;
    }

    @Override
    public IPhysicsObject getPhysicsObject() {
        return mPhysicsObject;
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawCircle(
                (float) (double) mCenter.getX(),
                (float) (double) mCenter.getY(),
                (float) mRaduis,
                mDefaultPaint);
    }
}
