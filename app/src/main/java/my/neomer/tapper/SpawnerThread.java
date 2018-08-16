package my.neomer.tapper;

import android.graphics.BitmapFactory;

import my.neomer.tapper.actors.Barrier;
import my.neomer.tapper.actors.Energy;
import my.neomer.tapper.actors.IActor;
import my.neomer.tapper.actors.StoneProjectile;
import my.neomer.tapper.actors.TreeActor;

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
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                return;
            }


            IActor actor = null;
            Coordinate coordinates = new Coordinate(1700, Math.abs(Math.random()) * 800 + 100);

            double random = Math.random();
            if (random > 0.33) {
                if (random < 0.2)
                {
                    Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.block));
                    actor = new Barrier(coordinates, sprite, defaultMaterial);
                }
                else if (random < 0.68)
                {
                    Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.stone_bullet), 10);
                    sprite.setAnimationSpeed(3);
                    sprite.setScale(0.2);
                    sprite.setAnimationType(Sprite.SpriteAnimationType.Circle);
                    sprite.Start();
                    actor = new StoneProjectile(coordinates, sprite, defaultMaterial);
                    actor.ApplyForce(mGameSurface.getGravity());
                }
                else
                {
                    Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.tree));
                    sprite.setScale(0.5);
                    actor = new TreeActor(coordinates, sprite, defaultMaterial);
                }
            } else {
                Sprite sprite = new Sprite(BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.energy), 8);
                sprite.Start();
                sprite.setScale(0.3);
                sprite.setAnimationType(Sprite.SpriteAnimationType.Circle);
                sprite.setAnimationSpeed(2);
                actor = new Energy(coordinates, sprite, defaultMaterial);
            }
            if (actor != null)
            {
                actor.ApplyImpulse(new Vector(-50, 0));
                mGameSurface.SpawnActor(actor);
            }

        }
    }
}
