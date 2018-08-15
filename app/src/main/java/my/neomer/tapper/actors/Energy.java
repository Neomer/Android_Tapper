package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;

public class Energy extends BaseActor {

    public Energy(Coordinate position, Sprite sprite, Material material) {
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
