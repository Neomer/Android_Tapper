package my.neomer.tapper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.security.ConfirmationAlreadyPresentingException;
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
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Renderer extends SurfaceView
{
    private boolean bRun;
    private List<IActor> mActors;
    private WorldUpdater mWorldUpdater;
    private PhysicsUpdater mPhysicsUpdater;
    private Spawner mBlockSpwaner;
    private HUD mHUD;
    private long mStartTime;
    private AssetManager mAssets;

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

    public AssetManager getAssets() {
        return mAssets;
    }

    public Renderer(Context context, AssetManager assets)
    {
        super(context);

        bRun = false;
        mDisplayFPS = false;
        mAssets = assets;

        mActors = new ArrayList<IActor>();
        mActorsLocker = new ReentrantLock();

        // Load global forces
        mGravity = new GravityForce();

        // Default material without reflections
        Material defaultMaterial = new Material();
        defaultMaterial.setElasticity(0);

        // Creating map
        Sprite mapStrite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.map));
        IActor mapActor = new MapActor(new Coordinate(0, 0), mapStrite, defaultMaterial);
        mapActor.ApplyImpulse(new Vector(-5, 0));
        SpawnActor(mapActor);

        //Creating HUD
        mHUD = new HUD(this);

        // Creating a main player
        Sprite sprite = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.eagle),
                4);
        sprite.setAnimationSpeed(3);
        sprite.setScale(0.5);
        sprite.Start();

        mPlayer = new PlayerActor(new Coordinate(50, 550), sprite, defaultMaterial);
        mPlayer.ApplyForce(mGravity);
        SpawnActor(mPlayer);

        // Creating world updater
        mWorldUpdater = new WorldUpdater(this);
        mWorldUpdater.setDaemon(true);
        mWorldUpdater.begin();

        //mPhysicsUpdater = new PhysicsUpdater(this);
        //mPhysicsUpdater.setDaemon(true);
        //mPhysicsUpdater.begin();

        mBlockSpwaner = new Spawner(this);
        mBlockSpwaner.setDaemon(true);

    }

    private void BeginPlay() {

        mStartTime = System.currentTimeMillis();

        mWorldUpdater.begin();
        mWorldUpdater.start();

        //mPhysicsUpdater.begin();
        //mPhysicsUpdater.start();

        mBlockSpwaner.begin();
        mBlockSpwaner.start();
    }

    private void StopPlay() {
        mWorldUpdater.End();
        //mPhysicsUpdater.end();
        mBlockSpwaner.end();
    }

    public long GetGameTime() {
        return System.currentTimeMillis() - mStartTime;
    }

    public PlayerActor Player() {
        return (PlayerActor)mPlayer;
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

            int backgroundColor = Color.parseColor("#160B0B");

            PlayerActor player = mRenderer.Player();

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
                    canvas = mRenderer.getHolder().lockCanvas();
                    synchronized (mRenderer.getHolder())
                    {
                        // Update physics
                        for (IActor actor : mActors)
                        {
                            if (!actor.IsDead())
                            {
                                actor.UpdatePhysics(elapsedPhys);
                                actor.getSprite().Update(elapsedPhys);

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

                                if (actor != player && actor.GetCollisionRegion() != null && actor.GetCollisionRegion().checkIntersect(player.GetCollisionRegion()))
                                {
                                    if (actor.CanKill())
                                    {
                                        mRenderer.Player().Kill();
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

                        // Draw scene
                        //canvas.drawColor(backgroundColor);
                        for (IActor actor : mActors)
                        {
                            if (!actor.IsDead())
                            {
                                actor.Draw(canvas);
                                // Draw collision regions
                                //Rect rect = actor.GetCollisionRegion().GetMappedRect(actor.GetCoordinates());
                                //canvas.drawRect(rect, mCollisionPainter);
                            }
                        }

                        // Draw HUD
                        mHUD.Draw(canvas);

                        // Draw FPS if needed
                        if (mRenderer.getDisplayFPS())
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
                        mRenderer.StopPlay();
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
            mRun = false;
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

                            if (actor != player && actor.GetCollisionRegion() != null && actor.GetCollisionRegion().checkIntersect(player.GetCollisionRegion()))
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

                IActor barrier = null;
                Coordinate coordinates = new Coordinate(1700, Math.abs(Math.random()) * 800 + 100);

                if (Math.random() > 0.5) {
                    Sprite sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.block));
                    barrier = new Barrier(coordinates, sprite, defaultMaterial);
                } else {
                    Sprite sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.energy), 8);
                    sprite.Start();
                    sprite.setScale(0.3);
                    sprite.setAnimationSpeed(2);
                    barrier = new Energy(coordinates, sprite, defaultMaterial);
                }

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
