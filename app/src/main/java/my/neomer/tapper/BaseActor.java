package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.health.TimerStat;
import android.support.annotation.NonNull;
import android.util.Log;

import java.security.cert.CertificateNotYetValidException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

abstract class BaseActor implements IActor {
    private Coordinate mPosition;
    private Vector mVelocity;
    private Sprite mSprite;
    private List<Vector> mForces;
    private Paint fontPaint;
    private Material mMaterial;

    private boolean mDead;

    BaseActor(Coordinate position, Sprite sprite, Material material)
    {
        mSprite = sprite;
        mPosition = position;

        mVelocity = new Vector();
        mForces = new ArrayList<Vector>();
        mMaterial = material;

        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(25);
        fontPaint.setARGB(255, 255, 0, 0);

        mDead = false;
    }

    @Override
    public Coordinate GetCoordinates() {
        return mPosition;
    }

    @Override
    public void Kill() {
        mDead = true;
    }

    @Override
    public boolean IsDead() {
        return mDead;
    }

    @Override
    public void Draw(Canvas canvas)
    {
        //canvas.drawText(String.format("Velocity: %s", mVelocity.toString()), 10, 20, fontPaint);
        //canvas.drawText(String.format("Position: %s", mPosition.toString()), 10, 40, fontPaint);
        Rect dstRect = new Rect(
                (int) mPosition.getX(),
                (int) mPosition.getY(),
                (int) (mPosition.getX() + mSprite.GetWidth()),
                (int) (mPosition.getY() + mSprite.GetHeight())),
            srcRect = mSprite.GetCurrentMask();

        canvas.drawBitmap(mSprite.GetBitmap(), srcRect, dstRect, null);
        //canvas.drawBitmap(mSprite.GetBitmap(), (int)mPosition.getX(), (int)mPosition.getY(), null);
    }

    @Override
    public void ApplyForce(Vector force)
    {
        mForces.add(force);
    }

    @Override
    public void ApplyImpulse(Vector impulse)
    {
        mVelocity.Add(impulse);
    }

    @Override
    public void UpdatePhysics(double timeSpan) {
        if (IsStatic() || IsDead()) {
            return;
        }

        updateVelocity(timeSpan);

        Vector v = mVelocity.Clone();
        v.Multiply(timeSpan);
        mPosition.Add(v);
    }

    @Override
    public void SetMaterial(Material material) {
        mMaterial = material;
    }

    private void updateVelocity(double timeSpan)
    {
        // Calculate result force
        Vector resultForce = new Vector();
        for (Vector v : mForces) {
            resultForce.Add(v);
        }
        resultForce.Multiply(timeSpan);
        mVelocity.Add(resultForce);
    }

    public Vector getVelocity() {
        return mVelocity;
    }

    public Sprite getSprite() {
        return mSprite;
    }
}
