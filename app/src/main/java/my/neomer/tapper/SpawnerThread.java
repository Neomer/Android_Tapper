package my.neomer.tapper;

import android.graphics.BitmapFactory;

public class SpawnerThread extends Thread
{
    GameSurface mGameSurface;
    boolean mRun;


    SpawnerThread(GameSurface gameSurface) {
        mGameSurface = gameSurface;
        mRun = false;
    }

    public void begin()
    {
        mRun = true;
    }

    public  void end()
    {
        mRun = false;
    }


    @Override
    public void run() {
        Material defaultMaterial = new Material();
        defaultMaterial.setElasticity(0);

        while (mRun)
        {
            IActor barrier = null;
            Coordinate coordinates = new Coordinate(1700, Math.abs(Math.random()) * 800 + 100);

            if (Math.random() > 0.5) {
                Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.block));
                barrier = new Barrier(coordinates, sprite, defaultMaterial);
            } else {
                Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.energy), 8);
                sprite.Start();
                sprite.setScale(0.3);
                sprite.setAnimationSpeed(2);
                barrier = new Energy(coordinates, sprite, defaultMaterial);
            }
            if (barrier != null)
            {
                barrier.ApplyImpulse(new Vector(-50, 0));
                mGameSurface.SpawnActor(barrier);
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
