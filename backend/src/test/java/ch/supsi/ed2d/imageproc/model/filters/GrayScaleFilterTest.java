package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayScaleFilterTest {

    @Test
    void apply() {
        Image img = new Image(3,3);
        assertDoesNotThrow(()->{
            int r = 1; int g = 2; int b = 3;
            for(int y = 0; y<img.getHeight(); y++)
                for(int x = 0; x<img.getWidth(); x++)
                    img.setPixel(Pixel.rgb(r++,g++,b++),x,y);
        });

        assertDoesNotThrow(()->{
            int[] results = {2,3,4,5,6,7,8,9,10};
            var gsImg = GrayScaleFilter.getInstance().apply(img);

            int i = 0;
            for(Pixel p : gsImg)
            {
                assertEquals(results[i],p.getR());
                assertEquals(results[i],p.getG());
                assertEquals(results[i++],p.getB());
                assertEquals(1,p.getFourthChannel());
            }
        });
    }
}