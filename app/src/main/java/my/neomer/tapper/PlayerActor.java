package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

class PlayerActor extends BaseActor implements IControllable
{
    private ICollisionRegion mCollisionRegion;

    PlayerActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);

        mCollisionRegion = new RectangleCollisionRegion(this, sprite.GetWidth(), sprite.GetHeight());
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return mCollisionRegion;
    }

    @Override
    public void Jump() {
        ApplyImpulse(new Vector(0, -10));
    }
}
