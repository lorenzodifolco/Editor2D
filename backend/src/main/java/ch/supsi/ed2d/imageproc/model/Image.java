package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.CheckedFunction;
import ch.supsi.ed2d.imageproc.InvalidImageException;

import java.util.*;
import java.util.function.Consumer;

public class Image implements Iterable<Pixel>{
    private final Pixel[][] pixels;
    private final int width;
    private final int height;

    public Image(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new Pixel[width][height];
    }

    public Image(Image img) throws InvalidImageException {
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.pixels = new Pixel[width][height];
        this.apply((x,y)->{
            var pixel = img.getPixel(x,y);
            this.pixels[x][y] = new Pixel(pixel.getFirstChannel(),pixel.getSecondChannel(),pixel.getThirdChannel(),pixel.getFourthChannel());
        });
    }

    public void apply(CheckedFunction<Integer,Integer> method) throws InvalidImageException
    {
        for(int y=0;y<height;y++)
            for(int x=0;x<width;x++)
            {
                 method.apply(x,y);
            }
    }

    public void setPixel(Pixel p, int x, int y)
    {
        this.pixels[x][y] = p;
    }

    public Pixel getPixel(int x, int y)
    {
        return pixels[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return width == image.width && height == image.height && Arrays.deepEquals(pixels, image.pixels);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.deepHashCode(pixels);
        return result;
    }

    @Override
    public Iterator<Pixel> iterator() {
        return new ImageIterator(this);
    }

    @Override
    public void forEach(Consumer<? super Pixel> action) {
        var iterator = new ImageIterator(this);
        while(iterator.hasNext())
        {
            action.accept(iterator.next());
        }
    }
}
