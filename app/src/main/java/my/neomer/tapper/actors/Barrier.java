package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;

public class Barrier extends BaseActor {

    public Barrier(Coordinate position, Sprite sprite, Material material) {
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
