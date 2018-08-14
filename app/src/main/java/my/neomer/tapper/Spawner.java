package my.neomer.tapper;

import android.graphics.BitmapFactory;

public class Spawner extends Thread
{
    Renderer mRenderer;
    boolean mRun;


    Spawner(Renderer renderer) {
        mRenderer = renderer;
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
                Sprite sprite = new Sprite(BitmapFactory.decodeResource(mRenderer.getResources(), R.drawable.block));
                barrier = new Barrier(coordinates, sprite, defaultMaterial);
            } else {
                Sprite sprite = new Sprite(BitmapFactory.decodeResource(mRenderer.getResources(), R.drawable.energy), 8);
                sprite.Start();
                sprite.setScale(0.3);
                sprite.setAnimationSpeed(2);
                barrier = new Energy(coordinates, sprite, defaultMaterial);
            }
            if (barrier != null)
            {
                barrier.ApplyImpulse(new Vector(-50, 0));
                mRenderer.SpawnActor(barrier);
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
