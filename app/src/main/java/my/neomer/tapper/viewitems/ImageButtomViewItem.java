package my.neomer.tapper.viewitems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageButtomViewItem extends AbstractViewItem {

    Paint mPaint;
    String mText;
    Bitmap mBitmap;

    public ImageButtomViewItem(Bitmap bitmap, String text, IViewItem parent) {
        super(parent);
        mText = text;

        mBitmap = bitmap;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public ImageButtomViewItem(Bitmap bitmap, String text, int width, int height, int left, int top, IViewItem parent) {
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
    }
}
