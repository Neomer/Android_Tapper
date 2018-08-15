package my.neomer.tapper;

import android.graphics.Canvas;

public class GroundBlock extends BaseActor {

    ICollisionRegion mCollisionRegion;

    GroundBlock(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        GetCollisionRegion().AddCollisionElement(
                new RectangleCollisionElement(this, sprite.GetWidth(), sprite.GetHeight()));

        ApplyImpulse(new Vector(-5, 0));
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
