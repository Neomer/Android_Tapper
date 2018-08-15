package my.neomer.tapper.actors;

import my.neomer.tapper.CircleCollisionElement;
import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;
import my.neomer.tapper.Vector;

public class StoneProjectile extends BaseProjectileActor {

    public StoneProjectile(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new CircleCollisionElement(
                        this,
                        new Coordinate(sprite.GetWidth() * 0.5, sprite.GetHeight() * 0.5),
                        50)
        );

        ApplyImpulse(new Vector(-30 - Math.random() * 30, -70 - Math.random() * 30));
    }

}
