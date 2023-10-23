package ch.supsi.ed2d.imageproc.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImageIterator implements Iterator<Pixel> {

    private final Image img;
    public ImageIterator(Image img)
    {
        this.img = img;
    }

    int x = 0;
    int y = 0;
    @Override
    public boolean hasNext() {
        return x<img.getWidth() && y<img.getHeight();
    }

    @Override
    public Pixel next() {
        if(!hasNext())
            throw new NoSuchElementException();
        else
        {
            var pix = img.getPixel(x,y);

            if(x == img.getWidth()-1) {
                x = 0;
                y++;
            }
            else x++;

            return pix;
        }
    }
}
