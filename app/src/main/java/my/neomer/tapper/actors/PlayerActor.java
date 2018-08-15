package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.IControllable;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;
import my.neomer.tapper.Vector;

public class PlayerActor extends BaseActor implements IControllable
{
    public static final double MAX_ENERGY  = 100; // Energy limit
    private static final int ENERGY_BOOST = 25;   // Energy value increase

    private double mEnergy;


    public PlayerActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, 0, 20, sprite.GetWidth(), sprite.GetHeight() - 40));
        mEnergy = PlayerActor.MAX_ENERGY;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public void Update(double timeSpan) {
        super.Update(timeSpan);

        mEnergy -= timeSpan * 0.9;
    }

    @Override
    public boolean CanKill() {
        return false;
    }

    @Override
    public double GetMass() {
        return 2;
    }

    @Override
    public void Jump() {
        if (mEnergy > 0)
        {
            ApplyImpulse(new Vector(0, -50));
        }
    }

    public double getEnergy() {
        return mEnergy;
    }

    public void AddEnergy() {
        mEnergy += ENERGY_BOOST;
    }

}
