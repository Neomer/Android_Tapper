package my.neomer.tapper.viewitems;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface IViewItem {

    int getWidth();
    int getHeight();
    int getTop();
    int getLeft();

    Rect getPadding();
    Rect getMargin();

    IViewItem getParentViewItem();

    void Render(Canvas canvas);

}
