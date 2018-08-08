package my.neomer.tapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.net.FileNameMap;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Renderer extends SurfaceView
{
    private boolean bRun;
    private List<IActor> mActors;
    private WorldUpdater mWorldUpdater;
    private PhysicsUpdater mPhysicsUpdater;
    private Spawner mBlockSpwaner;

    private Lock mActorsLocker;

    private GravityForce mGravity;

    private boolean mDisplayFPS;

    private IActor mPlayer;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!bRun)
        {
            bRun = true;
            BeginPlay();
        }

        ((PlayerActor) Player()).Jump();

        return true;
    }

    public Renderer(Context context)
    {
        super(context);

        bRun = false;
        mDisplayFPS = true;

        mActors = new ArrayList<IActor>();
        mActorsLocker = new ReentrantLock();

        // Load global forces
        mGravity = new GravityForce();

        // Creating a main player
        Sprite sprite = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.bird_sprite),
                2);
        sprite.setScale(0.5);

        Material defaultMaterial = new Material();
        defaultMaterial.setElasticity(0);

        mPlayer = new PlayerActor(new Coordinate(50, 550), sprite, defaultMaterial);
        mPlayer.ApplyForce(mGravity);
        SpawnActor(mPlayer);

        // Creating world updater
        mWorldUpdater = new WorldUpdater(this);
        mWorldUpdater.setDaemon(true);
        mWorldUpdater.begin();

        mPhysicsUpdater = new PhysicsUpdater(this);
        mPhysicsUpdater.setDaemon(true);
        mPhysicsUpdater.begin();

        mBlockSpwaner = new Spawner(this);
        mBlockSpwaner.setDaemon(true);
    }

    private void BeginPlay() {
        mWorldUpdater.begin();
        mWorldUpdater.start();

        mPhysicsUpdater.begin();
        mPhysicsUpdater.start();

        mBlockSpwaner.begin();
        mBlockSpwaner.start();
    }

    private void StopPlay() {
        mWorldUpdater.End();
        mPhysicsUpdater.end();
        mBlockSpwaner.end();
    }

    public IActor Player() {
        return mPlayer;
    }

    public void SpawnActor(IActor actor)
    {
        if (actor == null)
        {
            return;
        }

        mActorsLocker.lock();
        try {
            mActors.add(actor);
        }
        finally {
            mActorsLocker.unlock();
        }
    }

    public List<IActor> getActors()
    {
        return mActors;
        /*
        List<IActor> result = null;
        mActorsLocker.lock();
        try {
            result = mActors;
        }
        finally {
            mActorsLocker.unlock();
        }
        return result;
        */
    }

    public void setDisplayFPS(boolean mDisplayFPS) {
        this.mDisplayFPS = mDisplayFPS;
    }

    public boolean getDisplayFPS() {
        return mDisplayFPS;
    }


    private class WorldUpdater extends Thread
    {

        private Renderer mRenderer;
        private volatile boolean mRun = false;

        private Paint mTextPaint;


        WorldUpdater(Renderer renderer) {
            mRenderer = renderer;
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

        @Override
        public void run()
        {
            long start = System.currentTimeMillis();

            Paint mCollisionPainter = new Paint();
            mCollisionPainter.setARGB(50, 0,255,0);

            while (mRun)
            {
                long now = System.currentTimeMillis();
                double elapsed = now - start;
                start = now;

                Canvas canvas = null;
                try
                {
                    // подготовка Canvas-а
                    canvas = mRenderer.getHolder().lockCanvas();
                    synchronized (mRenderer.getHolder())
                    {
                        canvas.drawColor(Color.WHITE);

                        if (mRenderer.getDisplayFPS())
                        {
                            int y = canvas.getHeight() - 20;
                            Rect r = mRenderer.Player().GetCollisionRegion().GetMappedRect(mRenderer.Player().GetCoordinates());
                            canvas.drawText(String.format("FPS: %d W: %d H: %d RECT: %s",
                                    Math.round(1000 / elapsed),
                                    canvas.getWidth(),
                                    canvas.getHeight(),
                                    r.toString()), 10, y, mTextPaint);
                        }

                        for (IActor actor : mActors)
                        {
                            if (!actor.IsDead())
                            {
                                actor.Draw(canvas);
                                // Draw collision region
                                //Rect rect = actor.GetCollisionRegion().GetMappedRect(actor.GetCoordinates());
                                //canvas.drawRect(rect, mCollisionPainter);
                            }
                        }
                    }
                }
                catch (Exception e) { }
                finally
                {
                    if (canvas != null)
                    {
                        mRenderer.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    private class PhysicsUpdater extends Thread
    {
        private Renderer mRenderer;
        private volatile boolean mRun = false;

        private Paint mTextPaint;


        PhysicsUpdater(Renderer renderer) {
            mRenderer = renderer;
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setTextSize(25);
            mTextPaint.setARGB(255, 255,0,0);
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
        public void run()
        {
            long start = System.currentTimeMillis();
            IActor player = mRenderer.Player();

            while (mRun)
            {
                long now = System.currentTimeMillis();
                double elapsed = (now - start) * 0.005;
                start = now;
                try
                {
                    for (IActor actor : mActors)
                    {
                        if (!actor.IsDead())
                        {
                            actor.UpdatePhysics(elapsed);

                            Coordinate actorCoordinate = actor.GetCoordinates();

                            if (actorCoordinate.getX() <= 0 ||
                                    actorCoordinate.getY() <= 0 ||
                                    actorCoordinate.getY() >= 900)
                            {
                                actor.Kill();
                                if (actor == Player())
                                {
                                    mRenderer.StopPlay();
                                }
                            }

                            if (actor != player && actor.GetCollisionRegion().checkIntersect(player.GetCollisionRegion()))
                            {
                                mRenderer.Player().Kill();
                                mRenderer.StopPlay();
                            }
                        }
                    }
                }
                catch (Exception e) { }
                finally
                {
                }
            }
        }
    }

    private class Spawner extends Thread
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
            while (mRun)
            {
                Material defaultMaterial = new Material();
                defaultMaterial.setElasticity(0);

                Sprite sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.block));
                IActor barrier = new Barrier(new Coordinate(1000, Math.abs(Math.random()) * 900), sprite, defaultMaterial);

                barrier.ApplyImpulse(new Vector(-50, 0));

                mRenderer.SpawnActor(barrier);

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
