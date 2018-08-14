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
    private SceneRenderer mWorldUpdater;
    private Spawner mBlockSpwaner;
    private HUD mHUD;
    private long mStartTime;
    private AssetManager mAssets;

    private Lock mActorsLocker;

    private GravityForce mGravity;

    private boolean mDisplayFPS;

    private IActor mPlayer;

    private OnGameOverListener mOnGameOverLisener = null;

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
        mWorldUpdater = new SceneRenderer(this);
        mWorldUpdater.begin();

        mBlockSpwaner = new Spawner(this);

    }

    public HUD getHUD() {
        return mHUD;
    }

    public void BeginPlay() {

        mStartTime = System.currentTimeMillis();

        mWorldUpdater.begin();
        mWorldUpdater.start();

        mBlockSpwaner.begin();
        mBlockSpwaner.start();
    }

    public void StopPlay() {
        mWorldUpdater.End();
        mBlockSpwaner.end();

        if (mOnGameOverLisener != null)
        {
            GameResults gameResults = new GameResults();
            gameResults.setTotalTime(GetGameTime());

            try {
                if (mWorldUpdater.isAlive())
                {
                    mWorldUpdater.interrupt();
                    mWorldUpdater.join();
                }
            } catch (InterruptedException e) { }
            try {
                if (mBlockSpwaner.isAlive()) {
                    mBlockSpwaner.interrupt();
                    mBlockSpwaner.join();
                }
            } catch (InterruptedException e) { }

            mOnGameOverLisener.OnGameOver(gameResults);
        }
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

    public void setOnGameOverLisener(OnGameOverListener gameOverLisener) {
        this.mOnGameOverLisener = gameOverLisener;
    }

}
