package my.neomer.tapper;

public class MapActor extends BaseActor {

    MapActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

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
    public boolean IsDead() {
        return false;
    }
}
