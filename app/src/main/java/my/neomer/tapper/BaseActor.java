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

    private Vector mPosition;
    private BaseForce mVelocity;
    private Bitmap mBitmap;

    private List<IForce> mForces;


    BaseActor(Vector position, Bitmap sprite)
    {
        mBitmap = sprite;
        mPosition = position;

        mVelocity = new BaseForce();
        mForces = new ArrayList<IForce>();
    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawBitmap(mBitmap, mPosition.getX(), mPosition.getY(), null);
    }

    @Override
    public void ApplyForce(IForce force)
    {
        mForces.add(force);
    }

    @Override
    public void ApplyImpulse(IForce impulse)
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
        for (IForce force : mForces)
        {

        }
    }
}
