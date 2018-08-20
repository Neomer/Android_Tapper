package my.neomer.tapper.viewitems;

public class ImageViewItem extends AbstractViewItem {

    public ImageViewItem(IViewItem parent) {
        super(parent);
    }

    public ImageViewItem(int left, int top, int width, int height, IViewItem parent) {
        super(width, height, left, top, parent);
    }

}
