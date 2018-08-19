package my.neomer.tapper.viewitems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ButtonViewItem extends AbstractViewItem {

    Paint mPaint;
    String mText;

    public ButtonViewItem(String text, IViewItem parent) {
        super(parent);

        mText = text;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public ButtonViewItem(String text, int width, int height, int left, int top, IViewItem parent) {
        super(width, height, left, top, parent);

        mText = text;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    public void Render(Canvas canvas) {
        int left = getAbsoluteLeft();
        int top = getAbsoluteTop();
        canvas.drawRect(left, top, left + getWidth(), top + getHeight(), mPaint);
    }

    public void setBackgroundColor(int color) {
        mPaint.setColor(color);
    }

    public void setText(String text) {
        mText = text;
    }
}
