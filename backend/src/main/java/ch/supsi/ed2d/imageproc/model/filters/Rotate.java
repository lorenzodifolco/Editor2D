package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;

public class Rotate implements Filter{
    private final boolean rightRotation;

    public Rotate(boolean rightRotation) {
        this.rightRotation = rightRotation;
    }

    @Override
    public Image apply(Image img) throws InvalidImageException {
        Image out = new Image(img.getHeight(), img.getWidth());

        img.apply((x,y) -> {
            if (rightRotation)
                out.setPixel(img.getPixel(x,y),out.getWidth()-1-y, x);
            else
                out.setPixel(img.getPixel(img.getWidth()-1-x,y),y, x);
        });
        return out;
    }
}
