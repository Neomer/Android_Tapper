package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Currency;

public class Sprite
{
    private Bitmap mBitmap;
    private int mStatesCount;
    private double mCurrentState;
    private double mAnimationSpeed;
    private Point<Integer> mMask;
    private double mScale;
    private int mDirection;
    private boolean mAnimation;


    Sprite(Bitmap bitmap, Point<Integer> mask, int statesCount) {
        mBitmap = bitmap;
        mStatesCount = statesCount;
        mMask = mask;
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
        mAnimation = false;
    }

    Sprite(Bitmap bitmap, int statesCount) {
        mBitmap = bitmap;
        mStatesCount = statesCount;
        mMask = new Point<Integer>(bitmap.getWidth() / statesCount, bitmap.getHeight());
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
        mAnimation = false;
    }

    public Sprite(Bitmap bitmap) {
        mBitmap = bitmap;
        mStatesCount = 1;
        mMask = new Point<Integer>(bitmap.getWidth(), bitmap.getHeight());
        mAnimationSpeed = 1;
        mCurrentState = 0;
        mScale = 1;
        mDirection = 1;
        mAnimation = false;
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
            if (mStatesCount - mCurrentState <= 0) {
                mCurrentState = mStatesCount - 1;
                mDirection = -1;
            }

            if (mCurrentState <= 0) {
                mCurrentState = timeSpan * mAnimationSpeed;
                mDirection = 1;
            }
        }
    }

    Rect GetCurrentMask() {
        int dx = (int)mCurrentState;
        return new Rect((int)(dx * mMask.getX()), 0, (dx + 1) * mMask.getX(), mMask.getY());
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
