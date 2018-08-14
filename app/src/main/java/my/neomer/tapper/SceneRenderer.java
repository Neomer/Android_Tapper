package my.neomer.tapper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Iterator;
import java.util.Locale;

public class SceneRenderer extends Thread
{

    private GameSurface mGameSurface;
    private volatile boolean mRun = false;

    private Paint mTextPaint;


    SceneRenderer(GameSurface gameSurface) {
        mGameSurface = gameSurface;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(25);
        mTextPaint.setARGB(255, 255,0,0);
    }

    public void begin()
    {
        mRun = true;
    }

    public void End()
    {
        mRun = false;
    }

    /**
     * Updates physics, sprites and check collision intersections
     */
    private void updateActorStates(Canvas canvas, double timeSpan) {
        PlayerActor player = mGameSurface.getPlayer();

        for (IActor actor : mGameSurface.getActors())
        {
            if (!actor.IsDead())
            {
                actor.Update(timeSpan);
                actor.getSprite().Update(timeSpan);

                Coordinate actorCoordinate = actor.GetCoordinates();

                if (actor != mGameSurface.getMap() &&
                    (actorCoordinate.getX() <= 0 ||
                        actorCoordinate.getY() <= 0 ||
                        actorCoordinate.getY() >= canvas.getHeight()))
                {
                    actor.Kill();
                    if (actor == player)
                    {
                        mGameSurface.StopPlay();
                    }
                }

                if (actor != player && actor.GetCollisionRegion() != null && actor.GetCollisionRegion().checkIntersect(player.GetCollisionRegion()))
                {
                    if (actor.CanKill())
                    {
                        player.Kill();
                    }
                    else
                    {
                        actor.Kill();

                        if (actor instanceof Energy)
                        {
                            player.AddEnergy();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run()
    {
        long start = System.currentTimeMillis();

        Paint mCollisionPainter = new Paint();
        mCollisionPainter.setARGB(50, 0,255,0);

        int backgroundColor = Color.parseColor("#160B0B");

        PlayerActor player = mGameSurface.getPlayer();

        Canvas canvas = null;

        while (mRun)
        {
            long now = System.currentTimeMillis();
            double elapsed = now - start;
            start = now;

            double elapsedPhys = elapsed * 0.005;

            try
            {
                // подготовка Canvas-а
                canvas = mGameSurface.getHolder().lockCanvas();
                synchronized (mGameSurface.getHolder())
                {
                    // Update physics
                    updateActorStates(canvas, elapsedPhys);

                    // Draw scene
                    Iterator<IActor> actorsIterator = mGameSurface.getActors().iterator();

                    while (actorsIterator.hasNext())
                    {
                        IActor actor = actorsIterator.next();

                        if (!actor.IsDead())
                        {
                            actor.Draw(canvas);
                            // Draw collision regions
                            /*
                            ICollisionRegion collisionRegion = actor.GetCollisionRegion();
                            if (collisionRegion != null)
                            {
                                Rect rect = collisionRegion.GetMappedRect(actor.GetCoordinates().Clone());
                                canvas.drawRect(rect, mCollisionPainter);
                            }
                            */
                        }
                        else
                        {
                            actorsIterator.remove();
                        }
                    }

                    // Draw HUD
                    mGameSurface.getHUD().Draw(canvas);

                    // Draw FPS if needed
                    if (mGameSurface.getDisplayFPS())
                    {
                        int y = canvas.getHeight() - 20;
                        canvas.drawText(String.format(
                                Locale.ROOT,
                                "FPS: %d W: %d H: %d",
                                Math.round(1000 / elapsed),
                                canvas.getWidth(),
                                canvas.getHeight()), 10, y, mTextPaint);
                    }
                }
                if (player.IsDead())
                {
                    mGameSurface.StopPlay();
                }
            }
            catch (Exception e) { }
            finally
            {
                if (canvas != null)
                {
                    mGameSurface.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

