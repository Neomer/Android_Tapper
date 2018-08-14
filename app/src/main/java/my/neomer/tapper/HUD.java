package my.neomer.tapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;

public class HUD {

    private GameSurface mGameSurface;
    private Bitmap mBitmapEnergy;
    private Paint mTextPaint, mRectPaint;
    private double mHalfTextWidth;

    private static final int ENERGY_LENGTH = 200;

    HUD(GameSurface gameSurface) {
        mGameSurface = gameSurface;

        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f);

        Bitmap bitmapSrc = BitmapFactory.decodeResource(mGameSurface.getResources(), R.drawable.energy_hud_large);

        mBitmapEnergy = Bitmap.createBitmap(
                bitmapSrc,
                0, 0, bitmapSrc.getWidth(), bitmapSrc.getHeight(),
                matrix,
                true
        );

        Typeface mainFont = null;

        try
        {
            mainFont = Typeface.createFromAsset(mGameSurface.getAssets(), "fonts/main-font.otf");
        }
        catch (Exception ex) {}

        mTextPaint = new Paint();
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(mGameSurface.getResources().getColor(R.color.HUDfontTime));
        if (mainFont != null)
        {
            mTextPaint.setTypeface(mainFont);
        }

        mRectPaint = new Paint();
        mRectPaint.setColor(mGameSurface.getResources().getColor(R.color.HUDenergy));


        String sTime = "00:00.00";
        float w[] = new float[sTime.length()];;
        int wc = mTextPaint.getTextWidths(sTime, w);
        mHalfTextWidth = 0;
        for (int i = 0; i < wc; i++) {
            mHalfTextWidth += w[i];
        }
        mHalfTextWidth *= 0.5;
    }

    void Draw(Canvas canvas) {
        PlayerActor player = (PlayerActor) mGameSurface.Player();

        //canvas.drawRect(15, 30, mBitmapEnergy.getWidth() * 10, mBitmapEnergy.getHeight() + 50, mRectPaint);
        //canvas.drawText(String.valueOf(player.getEnergy()), 10, 20, mTextPaint);

        canvas.drawBitmap(mBitmapEnergy, 20, 40, null);

        int startPosition = mBitmapEnergy.getWidth() + 40;
        int length = (int) (player.getEnergy() / PlayerActor.MAX_ENERGY * ENERGY_LENGTH) + startPosition;
        
        canvas.drawRect(startPosition, 40, length >= startPosition ? length : startPosition, mBitmapEnergy.getHeight() + 40, mRectPaint);

        canvas.drawText(
                TimeHelper.StringTimeFromMilliseconds(mGameSurface.GetGameTime()),
                (int)(canvas.getWidth() * 0.5 - mHalfTextWidth * 0.5),
                40 + mTextPaint.getTextSize(),
                mTextPaint);

    }

}
