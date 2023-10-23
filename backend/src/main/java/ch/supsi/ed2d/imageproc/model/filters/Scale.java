package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;

import java.util.Objects;

public class Scale implements Filter{

    private final int width;

    private final int height;

    public Scale(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public Image apply(Image img) throws InvalidImageException {
        Image out = new Image(width,height);
        out.apply((x,y) -> {
                    int xp = x * img.getWidth() / width;
                    int yp = y * img.getHeight() / height;
                    out.setPixel(img.getPixel(xp,yp),x,y);
        });
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scale scale = (Scale) o;
        return width == scale.width && height == scale.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
