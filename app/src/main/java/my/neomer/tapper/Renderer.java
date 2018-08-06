package my.neomer.tapper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaCodec;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

    List<IActor> mActors;
    WorldUpdater mWorldUpdater;
    Lock mActorsLocker;

    public Renderer(Context context)
    {
        super(context);

        mActors = new ArrayList<IActor>();
        mActorsLocker = new ReentrantLock();

        mWorldUpdater = new WorldUpdater(this);
        mWorldUpdater.setDaemon(true);
        mWorldUpdater.begin();
        mWorldUpdater.start();
    }

    public void SpawnActor(IActor actor)
    {
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

    private class WorldUpdater extends Thread
    {

        private Renderer mRenderer;
        private volatile boolean mRun = false;


        WorldUpdater(Renderer renderer)
        {
            mRenderer = renderer;
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
                long elapsed = now - start;
                start = now;

                Canvas canvas = null;
                try
                {
                    // подготовка Canvas-а
                    canvas = mRenderer.getHolder().lockCanvas();
                    synchronized (mRenderer.getHolder())
                    {
                        drawSurface(canvas);

                        for (IActor actor : mActors)
                        {
                            actor.UpdatePhysics(elapsed);
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

        private void drawSurface(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
        }
    }

}
