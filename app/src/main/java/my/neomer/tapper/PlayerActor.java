package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

class PlayerActor extends BaseActor implements IControllable
{

    PlayerActor(Bitmap sprite, Vector position) {
        super(position, sprite);
    }

    @Override
    public void Jump() {
        ApplyForce(new BaseForce());
    }
}
