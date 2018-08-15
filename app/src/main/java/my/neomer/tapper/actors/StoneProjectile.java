package my.neomer.tapper.actors;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;
import my.neomer.tapper.Vector;

public class StoneProjectile extends BaseProjectileActor {

    public StoneProjectile(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, sprite.GetWidth(), sprite.GetHeight())
        );

        ApplyImpulse(new Vector(-30 - Math.random() * 30, -70 - Math.random() * 30));
    }

}
