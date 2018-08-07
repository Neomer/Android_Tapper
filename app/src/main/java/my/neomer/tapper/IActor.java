package my.neomer.tapper;

import android.graphics.Canvas;

public interface IActor extends IPhysicsObject {

    Coordinate GetCoordinates();

    void Draw(Canvas canvas);

}
