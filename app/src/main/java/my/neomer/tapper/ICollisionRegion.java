package my.neomer.tapper;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface ICollisionRegion {

    IPhysicsObject getPhysicsObject();

    boolean checkIntersect(ICollisionRegion collisionRegion);

    void Draw(Canvas canvas);

}
