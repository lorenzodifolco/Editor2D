package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;

public class Flip implements Filter{

    private final boolean verticalFlip;

    public Flip(boolean verticalFlip) {
        this.verticalFlip = verticalFlip;
    }


    @Override
    public Image apply(Image img) throws InvalidImageException {
        Image out = new Image(img.getWidth(), img.getHeight());
        out.apply((x,y) -> {
            if (verticalFlip) {
                int yp = img.getHeight() - y - 1;
                out.setPixel(img.getPixel(x, yp), x, y);
            } else {
                int xp = img.getWidth() - x - 1;
                out.setPixel(img.getPixel(xp, y), x, y);
            }
        });
        return out;
    }
}
