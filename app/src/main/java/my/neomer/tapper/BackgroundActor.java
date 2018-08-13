package my.neomer.tapper;

public class BackgroundActor extends BaseActor {

    @Override
    public boolean IsDead() {
        return false;
    }

    @Override
    public boolean CanKill() {
        return false;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return null;
    }

    BackgroundActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);
    }
}
