package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class WritableImageConverter {
    public static WritableImage convertImage(Image image)
    {
        WritableImage wimg = new WritableImage(image.getWidth(),image.getHeight());
        PixelWriter pw = wimg.getPixelWriter();
        for(int y = 0; y< image.getHeight(); y++)
            for(int x = 0; x< image.getWidth(); x++) {
                Pixel p = image.getPixel(x,y);
                pw.setColor(x, y, Color.color(p.getFirstChannel(), p.getSecondChannel(), p.getThirdChannel()));
            }
        return wimg;
    }
}
