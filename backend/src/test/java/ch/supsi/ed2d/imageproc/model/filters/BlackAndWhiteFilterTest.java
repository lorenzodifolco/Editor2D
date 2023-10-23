package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackAndWhiteFilterTest {

    @Test
    void apply() {
        Image img = new Image(3,3);
        assertDoesNotThrow(()->{
            int r = 0; int g = 0; int b = 0;
            for(int y = 0; y<img.getHeight(); y++)
                for(int x = 0; x<img.getWidth(); x++)
                    img.setPixel(Pixel.rgb(r++,g++,b++),x,y);
        });

        assertDoesNotThrow(()->{
            var bwImg = BlackAndWhiteFilter.getInstance().apply(img);
            int[] results = {0,0,0,1,1,1,1,1,1};
            int i = 0;
            for (Pixel p: bwImg) {
                assertEquals(results[i],p.getFirstChannel());
                assertEquals(results[i],p.getSecondChannel());
                assertEquals(results[i++],p.getThirdChannel());
                assertEquals(1,p.getFourthChannel());
            }
        });
    }
}