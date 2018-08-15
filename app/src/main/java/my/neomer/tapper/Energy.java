package my.neomer.tapper;

public class Energy extends BaseActor {

    Energy(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, 10,0, sprite.GetWidth() - 20, sprite.GetHeight()));
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public boolean CanKill() {
        return false;
    }
}
