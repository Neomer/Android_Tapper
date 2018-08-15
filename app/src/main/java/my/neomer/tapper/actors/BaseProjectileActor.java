package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.Sprite;

public abstract class BaseProjectileActor extends BaseActor {

    public BaseProjectileActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);
    }

    @Override
    public boolean CanKill() {
        return true;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

}
