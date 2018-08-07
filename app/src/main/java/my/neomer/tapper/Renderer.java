package my.neomer.tapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private List<IActor> mActors;
    private WorldUpdater mWorldUpdater;
    private PhysicsUpdater mPhysicsUpdater;

    private Lock mActorsLocker;

    private GravityForce mGravity;

    private boolean mDisplayFPS;

    private IActor mPlayer;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ((PlayerActor) Player()).Jump();

        return true;
    }

    public Renderer(Context context)
    {
        super(context);

        mDisplayFPS = true;

        mActors = new ArrayList<IActor>();
        mActorsLocker = new ReentrantLock();

        // Load global forces
        mGravity = new GravityForce();

        // Creating a main player
        Bitmap sprite = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);

        Material defaultMaterial = new Material();
        defaultMaterial.setElasticity(0);

        mPlayer = new PlayerActor(new Coordinate(50, 550), sprite, defaultMaterial);
        SpawnActor(mPlayer);

        // Creating world updater
        mWorldUpdater = new WorldUpdater(this);
        mWorldUpdater.setDaemon(true);
        mWorldUpdater.begin();
        mWorldUpdater.start();

        mPhysicsUpdater = new PhysicsUpdater(this);
        mPhysicsUpdater.setDaemon(true);
        mPhysicsUpdater.begin();
        mPhysicsUpdater.start();
    }

    public IActor Player() {
        return mPlayer;
    }

    public void SpawnActor(IActor actor)
    {
        mActorsLocker.lock();
        try {
            mActors.add(actor);
            actor.ApplyForce(mGravity);
        }
        finally {
            mActorsLocker.unlock();
        }
    }

    public List<IActor> getActors()
    {
        List<IActor> result = null;
        mActorsLocker.lock();
        try {
            result = mActors;
        }
        finally {
            mActorsLocker.unlock();
        }
        return result;
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

        @Override
        public void run()
        {
            long start = System.currentTimeMillis();
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
                            canvas.drawText(String.format("FPS: %d", Math.round(1000 / elapsed)), 10, y, mTextPaint);
                        }

                        for (IActor actor : mActors)
                        {
                            actor.Draw(canvas);
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

        @Override
        public void run()
        {
            long start = System.currentTimeMillis();
            while (mRun)
            {
                long now = System.currentTimeMillis();
                double elapsed = (now - start) * 0.005;
                start = now;

                Canvas canvas = null;
                try
                {
                    for (IActor actor : mActors)
                    {
                        actor.UpdatePhysics(elapsed);
                    }
                }
                catch (Exception e) { }
                finally
                {
                }
            }
        }
    }

}
