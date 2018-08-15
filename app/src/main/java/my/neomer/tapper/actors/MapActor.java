package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.Sprite;

public class MapActor extends BaseActor {

    public MapActor(Coordinate position, Sprite sprite, Material material) {
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
