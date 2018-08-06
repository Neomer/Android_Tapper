package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

class PlayerActor extends BaseActor implements IControllable
{

    PlayerActor(Coordinate position, Bitmap sprite) {
        super(position, sprite);
    }

    @Override
    public void Jump() {
    }
}
