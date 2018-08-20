package my.neomer.tapper.viewitems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageButtonViewItem extends AbstractViewItem {

    Paint mPaint;
    String mText;
    Bitmap mBitmap;

    public ImageButtonViewItem(Bitmap bitmap, String text, IViewItem parent) {
        super(parent);
        mText = text;

        mBitmap = bitmap;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public ImageButtonViewItem(Bitmap bitmap, String text, int left, int top, int width, int height, IViewItem parent) {
        super(width, height, left, top, parent);
        mText = text;

        mBitmap = bitmap;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    public void Render(Canvas canvas) {
        if (mBitmap != null) {

        }

        super.Render(canvas);
    }
}
