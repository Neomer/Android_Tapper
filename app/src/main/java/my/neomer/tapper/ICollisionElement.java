package my.neomer.tapper;

import android.graphics.Canvas;

public interface ICollisionElement {

    boolean checkIntersect(ICollisionElement collisionElement);

    IPhysicsObject getPhysicsObject();

    void Draw(Canvas canvas);


}
