package my.neomer.tapper.viewitems;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractViewItem implements IViewItem {

    IViewItem mParent;
    int mWidth, mHeight, mTop, mLeft;
    Rect mPadding, mMargin;
    List<IViewItem> mChildrenViewItem;

    OnViewItemEventListener mViewItemEventListener;

    public AbstractViewItem(IViewItem parent) {
        mParent = parent;
        mWidth = 0;
        mHeight = 0;
        mTop = 0;
        mLeft = 0;

        mPadding = new Rect(0, 0, 0, 0);
        mMargin = new Rect(0,0,0,0);

        mChildrenViewItem = new ArrayList<IViewItem>();

        mViewItemEventListener = null;
    }

    @Override
    @Nullable
    public IViewItem findChildByPoint(int x, int y) {
        IViewItem result = null;

        for (IViewItem viewItem : mChildrenViewItem) {
            result  = viewItem.findChildByPoint(x, y);
            if (result != null) {
                return result;
            }
        }

        int left = getAbsoluteLeft(),
                top = getAbsoluteTop();

        if (x >= left && x <= left + getWidth() &&
                y >= top && y <= top + getHeight())
        {
            if (mViewItemEventListener != null) {
                mViewItemEventListener.OnClick(this);
            }
            return this;
        }

        return result;
    }

    @Override
    public void setViewItemEventListener(OnViewItemEventListener viewItemEventListener) {
        mViewItemEventListener = viewItemEventListener;
    }

    public AbstractViewItem(int width, int height, int left, int top, IViewItem parent) {
        mParent = parent;
        mWidth = width;
        mHeight = height;
        mTop = top;
        mLeft = left;

        mPadding = new Rect(0, 0, 0, 0);
        mMargin = new Rect(0,0,0,0);

        mChildrenViewItem = new ArrayList<IViewItem>();

        mViewItemEventListener = null;
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

    @Override
    public void Render(Canvas canvas) {
        for (IViewItem viewItem : mChildrenViewItem) {
            viewItem.Render(canvas);
        }
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

    public void AddChildViewItem(IViewItem viewItem) {
        mChildrenViewItem.add(viewItem);
    }
}
