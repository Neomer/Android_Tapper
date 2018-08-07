package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

class PlayerActor extends BaseActor implements IControllable
{

    PlayerActor(Coordinate position, Bitmap sprite, Material material) {
        super(position, sprite, material);
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public void Jump() {
        ApplyImpulse(new Vector(0, -10));
    }
}
