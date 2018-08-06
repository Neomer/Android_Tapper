package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

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


    BaseActor(Coordinate position, Bitmap sprite)
    {
        mBitmap = sprite;
        mPosition = position;

        mVelocity = new Vector();
        mForces = new ArrayList<Vector>();
    }

    @Override
    public void Draw(Canvas canvas)
    {
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

    }

    @Override
    public void UpdatePhysics(long timespan)
    {
        updateVelocity(timespan);
    }

    private void updateVelocity(long timespan)
    {
        // Calculate result force
        Vector resultForce = new Vector();
        for (Vector v : mForces) {
            resultForce.Add(v);
        }
    }
}
