package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.util.Iterator;
import java.util.Locale;

import my.neomer.tapper.actors.Energy;
import my.neomer.tapper.actors.IActor;
import my.neomer.tapper.actors.MapActor;
import my.neomer.tapper.actors.PlayerActor;

public class SceneRenderer extends Thread
{

    private GameSurface mGameSurface;
    private volatile boolean mRun = false;

    private Paint mTextPaint;
    private Bitmap backBitmap;


    SceneRenderer(GameSurface gameSurface) {
        mGameSurface = gameSurface;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(25);
        mTextPaint.setARGB(255, 255,0,0);
        backBitmap = BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.back);
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
        //Log.d("app", "updateActorStates() - start");

        PlayerActor player = mGameSurface.getPlayer();
        MapActor mapActor = mGameSurface.getMap();

        if (player.GetCoordinates().getY() >= canvas.getHeight())
        {
            player.Kill();
            mGameSurface.StopPlay();
        }

        for (IActor actor : mGameSurface.getActors())
        {
            if (!actor.IsDead())
            {
                actor.Update(timeSpan);
                actor.getSprite().Update(timeSpan);

                Coordinate actorCoordinate = actor.GetCoordinates();

                if (actorCoordinate.getX() + actor.getSprite().GetWidth() <= 0) {
                    actor.Kill();
                }

                if (actor != player &&
                        actor.GetCollisionRegion() != null &&
                        actor.GetCollisionRegion().hasElements() &&
                        actor.GetCollisionRegion().checkIntersect(player.GetCollisionRegion()))
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

                    if (canvas == null)
                    {
                        continue;
                    }

                    // Draw scene
                    /*
                    Rect srcRect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight()),
                        dstRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());

                    canvas.drawBitmap(backBitmap,srcRect ,dstRect, null);
                    */
                    canvas.drawColor(ResourcesCompat.getColor(mGameSurface.getResources(), R.color.BackgroundSky, null));

                    Iterator<IActor> actorsIterator = mGameSurface.getActors().iterator();

                    while (actorsIterator.hasNext())
                    {
                        IActor actor = actorsIterator.next();

                        if (!actor.IsDead() && actor.IsVisible(canvas))
                        {
                            actor.Draw(canvas);
                            // Draw collision regions
                            /*
                            ICollisionRegion collisionRegion = actor.GetCollisionRegion();
                            if (collisionRegion != null)
                            {
                                collisionRegion.Draw(canvas);
                            }
                            */
                        }
                        else
                        {
                            //actorsIterator.remove();
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

                // Update physics
                updateActorStates(canvas, elapsedPhys);

                mGameSurface.Unlock();
            }
            catch (Exception e) {
                Log.d("app", Log.getStackTraceString(e));
                return;
            }
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

