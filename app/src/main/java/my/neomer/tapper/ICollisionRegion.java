package my.neomer.tapper;

import android.graphics.Rect;

public interface ICollisionRegion {

    Rect GetMappedRect(Coordinate point);

    boolean checkIntersect(ICollisionRegion collisionRegion);

}
