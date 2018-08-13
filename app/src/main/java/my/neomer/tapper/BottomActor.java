package my.neomer.tapper;

import android.graphics.Canvas;

public class BottomActor extends BaseActor {
    BottomActor(Coordinate position, Sprite sprite, Material material) {
        super(position, sprite, material);
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public boolean IsDead() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return null;
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
    }

    @Override
    public boolean CanKill() {
        return true;
    }
}
