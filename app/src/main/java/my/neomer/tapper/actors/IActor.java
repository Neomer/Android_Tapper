package my.neomer.tapper.actors;

import android.graphics.Canvas;

import my.neomer.tapper.IPhysicsObject;
import my.neomer.tapper.Sprite;
import my.neomer.tapper.Vector;

public interface IActor extends IPhysicsObject {

    void Draw(Canvas canvas);

    Sprite getSprite();

    Vector getVelocity();

    boolean CanKill();

    boolean IsVisible(Canvas canvas);

}
