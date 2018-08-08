package my.neomer.tapper;

import android.graphics.Canvas;

public interface IActor extends IPhysicsObject {

    void Draw(Canvas canvas);

    Sprite getSprite();
    Vector getVelocity();

    boolean CanKill();

}
