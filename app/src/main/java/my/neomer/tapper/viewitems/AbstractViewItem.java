package my.neomer.tapper.viewitems;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class AbstractViewItem implements IViewItem {

    IViewItem mParent;
    int mWidth, mHeight, mTop, mLeft;
    Rect mPadding, mMargin;

    public AbstractViewItem(IViewItem parent) {
        mParent = parent;
        mWidth = 0;
        mHeight = 0;
        mTop = 0;
        mLeft = 0;
        mPadding = new Rect(0, 0, 0, 0);
        mMargin = new Rect(0,0,0,0);
    }

    public AbstractViewItem(int width, int height, int left, int top, IViewItem parent) {
        mParent = parent;
        mWidth = width;
        mHeight = height;
        mTop = top;
        mLeft = left;
        mPadding = new Rect(0, 0, 0, 0);
        mMargin = new Rect(0,0,0,0);
    }

    public void setPosition(int left, int top) {
        mLeft = left;
        mTop = top;
    }

    public void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    @Override
    public int getTop() {
        return mTop;
    }

    public void setTop(int top) {
        mTop = top;
    }

    @Override
    public int getLeft() {
        return mLeft;
    }

    @Override
    public Rect getPadding() {
        return mPadding;
    }

    @Override
    public Rect getMargin() {
        return mMargin;
    }

    public void setLeft(int left) {
        mLeft = left;
    }

    @Override
    public IViewItem getParentViewItem() {
        return mParent;
    }

    public int getAbsoluteLeft() {
        IViewItem parent = getParentViewItem();
        int result = getLeft();
        while (parent != null) {
            result += parent.getLeft();
            parent = parent.getParentViewItem();
        }
        return result;
    }

    public int getAbsoluteTop() {
        IViewItem parent = getParentViewItem();
        int result = getTop();
        while (parent != null) {
            result += parent.getTop();
            parent = parent.getParentViewItem();
        }
        return result;
    }

}
