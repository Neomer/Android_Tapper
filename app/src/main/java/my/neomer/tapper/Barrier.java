package my.neomer.tapper;

import android.graphics.Bitmap;

public class Barrier extends BaseActor {

    private ICollisionRegion mCollisionRegion;

    Barrier(Coordinate position, Bitmap sprite, Material material) {
        super(position, sprite, material);

        mCollisionRegion = new RectangleCollisionRegion(this, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return mCollisionRegion;
    }

}
