package my.neomer.tapper.actors;

import android.graphics.Canvas;

import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;

public class TreeActor extends BaseActor {

    public TreeActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(new RectangleCollisionElement(
                this, 0, 200, 150, 150
        ));
/*
        GetCollisionRegion().AddCollisionElement(new RectangleCollisionElement(
                this, 400, 0, 200, sprite.GetHeight()
        ));
*/

        GetCollisionRegion().AddCollisionElement(new RectangleCollisionElement(
                this, sprite.GetWidth() - 150, 200, 150, 150
        ));
    }

    @Override
    public void Draw(Canvas canvas) {
        GetCoordinates().setY(canvas.getHeight() - getSprite().GetHeight() - 20);
        super.Draw(canvas);
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