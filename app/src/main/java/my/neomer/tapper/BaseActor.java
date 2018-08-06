package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private Bitmap mBitmap;

    private List<Vector> mForces;

    Paint fontPaint;

    BaseActor(Coordinate position, Bitmap sprite)
    {
        mBitmap = sprite;
        mPosition = position;

        mVelocity = new Vector();
        mForces = new ArrayList<Vector>();

        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(25);
        fontPaint.setARGB(255, 255, 0, 0);
    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawText(String.format("Velocity: %s", mVelocity.toString()), 10, 20, fontPaint);
        canvas.drawBitmap(mBitmap, (int) mPosition.getX(),(int) mPosition.getY(), null);
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
    public void UpdatePhysics(double timespan) {
        updateVelocity(timespan);
        //Vector v = mVelocity;
        //v.Multiply(timespan);
        mPosition.Add(mVelocity);
    }

    private void updateVelocity(double timespan)
    {
        // Calculate result force
        Vector resultForce = new Vector();
        for (Vector v : mForces) {
            resultForce.Add(v);
        }
        resultForce.Multiply(timespan);
        mVelocity.Add(resultForce);
    }
}
