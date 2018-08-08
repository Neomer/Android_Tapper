package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite
{
    private Bitmap mBitmap;
    private int mStatesCount;
    private double mCurrentState;
    private double mAnimationSpeed;
    private Coordinate mMask;
    private double mScale;
    private int mDirection;
    private boolean mAnimation;


    Sprite(Bitmap bitmap, Coordinate mask, int statesCount) {
        mBitmap = bitmap;
        mStatesCount = statesCount;
        mMask = mask;
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
    }

    Sprite(Bitmap bitmap, int statesCount) {
        mBitmap = bitmap;
        mStatesCount = statesCount;
        mMask = new Coordinate(bitmap.getWidth() / statesCount, bitmap.getHeight());
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
    }

    public Sprite(Bitmap bitmap) {
        mBitmap = bitmap;
        mStatesCount = 1;
        mMask = new Coordinate(bitmap.getWidth(), bitmap.getHeight());
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
    }

    void Start() {
        mAnimation = true;
    }

    void Stop() {
        mAnimation = false;
    }

    void Update(double timeSpan) {
        if (mAnimation) {
            mCurrentState += (timeSpan * mAnimationSpeed * mDirection);
            if (mCurrentState >= mStatesCount) {
                mCurrentState -= mStatesCount;
            }
        }
    }

    Rect GetCurrentMask() {
        int dx = (int)mCurrentState;
        return new Rect((int)(dx * mMask.getX()), 0, (int)((dx + 1) * mMask.getX()), (int)mMask.getY());
    }

    Bitmap GetBitmap() {
        return mBitmap;
    }

    double GetWidth() {
        return mMask.getX() * mScale;
    }

    double GetHeight() {
        return mMask.getY() * mScale;
    }

    public double getScale() {
        return mScale;
    }

    public void setScale(double scale) {
        this.mScale = scale;
    }

    public void setAnimationSpeed(double animationSpeed) {
        mAnimationSpeed = animationSpeed;
    }
}
