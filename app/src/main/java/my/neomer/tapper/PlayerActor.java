package my.neomer.tapper;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.lang.invoke.ConstantCallSite;

class PlayerActor extends BaseActor implements IControllable
{
    public static final double MAX_ENERGY  = 100; // Energy limit
    private static final int ENERGY_BOOST = 25;   // Energy value increase

    private ICollisionRegion mCollisionRegion;
    private double mEnergy;


    PlayerActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        mCollisionRegion = new RectangleCollisionRegion(this, sprite.GetWidth(), sprite.GetHeight());
        mEnergy = PlayerActor.MAX_ENERGY;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return mCollisionRegion;
    }

    @Override
    public void UpdatePhysics(double timeSpan) {
        super.UpdatePhysics(timeSpan);

        mEnergy -= timeSpan;
    }

    @Override
    public boolean CanKill() {
        return false;
    }

    @Override
    public boolean IsDead() {
        return super.IsDead() || getEnergy() <= 0;
    }

    @Override
    public void Jump() {
        ApplyImpulse(new Vector(0, -10));
    }

    public double getEnergy() {
        return mEnergy;
    }

    public void AddEnergy() {
        mEnergy += ENERGY_BOOST;
    }

}
