package my.neomer.tapper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameSurface extends SurfaceView
{
    private boolean bRun;
    private List<IActor> mActors;
    private SceneRenderer sceneRenderer;
    private SpawnerThread spawnerThread;
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

    public GameSurface(Context context, AssetManager assets)
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
        sceneRenderer = new SceneRenderer(this);
        sceneRenderer.begin();

        spawnerThread = new SpawnerThread(this);

    }

    public HUD getHUD() {
        return mHUD;
    }

    public void BeginPlay() {

        mStartTime = System.currentTimeMillis();

        sceneRenderer.begin();
        sceneRenderer.start();

        spawnerThread.begin();
        spawnerThread.start();
    }

    public void StopPlay() {
        sceneRenderer.End();
        spawnerThread.end();

        if (mOnGameOverLisener != null)
        {
            GameResults gameResults = new GameResults();
            gameResults.setTotalTime(GetGameTime());

            try {
                if (sceneRenderer.isAlive())
                {
                    sceneRenderer.interrupt();
                    sceneRenderer.join();
                }
            } catch (InterruptedException e) { }
            try {
                if (spawnerThread.isAlive()) {
                    spawnerThread.interrupt();
                    spawnerThread.join();
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
