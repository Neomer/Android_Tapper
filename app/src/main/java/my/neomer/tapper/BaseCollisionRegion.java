package my.neomer.tapper;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class BaseCollisionRegion implements ICollisionRegion {

    private List<ICollisionElement> collisionElements;

    BaseCollisionRegion() {
        collisionElements = new ArrayList<ICollisionElement>();
    }

    @Override
    public List<ICollisionElement> getCollisionElements() {
        return collisionElements;
    }

    @Override
    public void AddCollisionElement(ICollisionElement collisionElement) {
        collisionElements.add(collisionElement);
    }

    @Override
    public boolean hasElements() {
        return !collisionElements.isEmpty();
    }

    @Override
    public boolean checkIntersect(ICollisionRegion collisionRegion) {

        for (ICollisionElement myCollisionElement : collisionElements) {
            for (ICollisionElement otherCollisionElement : collisionRegion.getCollisionElements()) {
                if (myCollisionElement.checkIntersect(otherCollisionElement))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void Draw(Canvas canvas) {
        for (ICollisionElement collisionElement : collisionElements) {
            collisionElement.Draw(canvas);
        }
    }
}
