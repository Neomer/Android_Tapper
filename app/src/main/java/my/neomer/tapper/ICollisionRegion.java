package my.neomer.tapper;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

public interface ICollisionRegion {

    boolean checkIntersect(ICollisionRegion collisionRegion);

    void AddCollisionElement(ICollisionElement collisionElement);

    List<ICollisionElement> getCollisionElements();

    boolean hasElements();

    void Draw(Canvas canvas);

}
