package my.neomer.tapper;

class PlayerActor extends BaseActor implements IControllable
{
    public static final double MAX_ENERGY  = 100; // Energy limit
    private static final int ENERGY_BOOST = 25;   // Energy value increase

    private ICollisionRegion mCollisionRegion;
    private double mEnergy;


    PlayerActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        mCollisionRegion = new RectangleCollisionRegion(this, 0, 20, sprite.GetWidth(), sprite.GetHeight() - 40);
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
    public void Update(double timeSpan) {
        super.Update(timeSpan);

        mEnergy -= timeSpan;
    }

    @Override
    public boolean CanKill() {
        return false;
    }

    @Override
    public void Jump() {
        if (mEnergy > 0)
        {
            ApplyImpulse(new Vector(0, -10));
        }
    }

    public double getEnergy() {
        return mEnergy;
    }

    public void AddEnergy() {
        mEnergy += ENERGY_BOOST;
    }

}
