package my.neomer.tapper;

public class Barrier extends BaseActor {

    Barrier(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, sprite.GetWidth(), sprite.GetHeight()));
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public boolean CanKill() {
        return true;
    }
}
