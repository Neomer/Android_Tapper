package my.neomer.tapper;

public class Energy extends BaseActor {

    private ICollisionRegion mCollisionRegion;

    Energy(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        mCollisionRegion = new RectangleCollisionRegion(this, sprite.GetWidth(), sprite.GetHeight());
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
    public boolean CanKill() {
        return false;
    }
}
