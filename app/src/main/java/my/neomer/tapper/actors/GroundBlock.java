package my.neomer.tapper.actors;

import android.graphics.Canvas;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.ICollisionRegion;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;
import my.neomer.tapper.Vector;

public class GroundBlock extends BaseActor {

    public GroundBlock(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, sprite.GetWidth(), sprite.GetHeight()));

        ApplyImpulse(new Vector(-50, 0));
    }

    @Override
    public boolean CanKill() {
        return true;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public void Draw(Canvas canvas) {
        GetCoordinates().setY(canvas.getHeight() - getSprite().GetHeight());
        super.Draw(canvas);
    }
}
