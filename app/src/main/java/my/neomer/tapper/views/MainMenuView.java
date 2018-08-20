package my.neomer.tapper.views;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import my.neomer.tapper.viewitems.IViewItem;
import my.neomer.tapper.viewitems.ImageViewItem;

public class MainMenuView implements ISurfaceView {

    IViewItem mRootViewItem;

    public MainMenuView() {
        
    }

    @Override
    public void Render(Canvas canvas) {

    }

    @Override
    @NonNull
    public IViewItem getRootViewItem() {
        return mRootViewItem;
    }
}
