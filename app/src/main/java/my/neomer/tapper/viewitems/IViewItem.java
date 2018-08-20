package my.neomer.tapper.viewitems;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface IViewItem {

    int getWidth();

    int getHeight();

    int getTop();

    int getLeft();

    int getAbsoluteLeft();

    int getAbsoluteTop();


    Rect getPadding();

    Rect getMargin();

    IViewItem getParentViewItem();

    void Render(Canvas canvas);

    void AddChildViewItem(IViewItem viewItem);

    void setViewItemEventListener(OnViewItemEventListener viewItemEventListener);

    IViewItem findChildByPoint(int x, int y);

}
