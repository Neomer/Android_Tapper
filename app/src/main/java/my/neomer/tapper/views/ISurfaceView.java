package my.neomer.tapper.views;

import android.graphics.Canvas;

import my.neomer.tapper.viewitems.IViewItem;

public interface ISurfaceView {

    void Render(Canvas canvas);

    IViewItem getRootViewItem();

}
