package my.neomer.tapper.actors;

import android.graphics.Canvas;

import my.neomer.tapper.CircleCollisionElement;
import my.neomer.tapper.Coordinate;
import my.neomer.tapper.Material;
import my.neomer.tapper.RectangleCollisionElement;
import my.neomer.tapper.Sprite;

public class TreeActor extends BaseActor {

    public TreeActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(new CircleCollisionElement(
                this, new Coordinate(90.0, 270.0), 70.0
        ));
        GetCollisionRegion().AddCollisionElement(new CircleCollisionElement(
                this, new Coordinate(180, 120), 100
        ));
        GetCollisionRegion().AddCollisionElement(new CircleCollisionElement(
                this, new Coordinate(sprite.GetWidth() - 90, 330), 60
        ));
    }

    @Override
    public void Draw(Canvas canvas) {
        GetCoordinates().setY(canvas.getHeight() - getSprite().GetHeight() - 80);
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