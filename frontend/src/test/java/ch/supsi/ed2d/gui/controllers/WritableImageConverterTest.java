package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class WritableImageConverterTest {

    @Test
    void convertImage() {
        assertDoesNotThrow(()->{
            Image img = new Image(5,6);
            img.apply((x,y)->img.setPixel(Pixel.rgb(0,0,0),x,y));

            WritableImage wimg = WritableImageConverter.convertImage(img);
            for(int y = 0; y<wimg.getHeight(); y++)
                for (int x = 0; x<wimg.getWidth(); x++)
                    assertEquals(javafx.scene.paint.Color.BLACK, wimg.getPixelReader().getColor(x,y));
        });
    }
}