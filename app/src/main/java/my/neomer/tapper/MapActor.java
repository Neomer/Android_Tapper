package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MapActor extends BaseActor {

    Coordinate mMapSizes, mMapClip;
    Byte [][] mMap;
    Sprite mBrick;

    MapActor(Coordinate position, Sprite brickBitmap, Material material) {
        super(position, null, material);

        mMapSizes = new Coordinate(83, 29);
        mMapClip = new Coordinate(55, 29);
        mMap = new Byte [][] {
            {}
        };


        mBrick = brickBitmap;
    }

    @Override
    public void Draw(Canvas canvas) {
        double dx = (canvas.getWidth() - (canvas.getWidth() % mMapClip.getX())) / mMapClip.getX();
        double dy = (canvas.getHeight() - (canvas.getHeight() % mMapClip.getY())) / mMapClip.getY();


        for (Byte x = 0; x < mMapClip.getX(); x++)
        {
            for (Byte y = 0; y < mMapClip.getY(); y++)
            {
                Rect dstRect = new Rect(
                        (int)(x * dx),
                        (int)(y * dy),
                        (int)((x + 1) * dx),
                        (int)((y + 1) * dy)),
                srcRect = mBrick.GetCurrentMask();

                switch (mMap[y][x])
                {
                    case 1:
                        canvas.drawBitmap(mBrick.GetBitmap(), srcRect, dstRect, null);
                        break;

                    default:
                        continue;
                }
            }
        }
    }

    void CreateActors() {
    }

    @Override
    public boolean CanKill() {
        return false;
    }

    @Override
    public boolean IsStatic() {
        return false;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return null;
    }

    @Override
    public boolean IsDead() {
        return false;
    }
}
