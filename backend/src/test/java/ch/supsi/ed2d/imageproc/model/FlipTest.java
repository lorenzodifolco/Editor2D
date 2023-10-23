package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.model.filters.Flip;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlipTest {

    @Test
    public void test01() {
        //Horizontal Flip test
        assertDoesNotThrow(() -> {
            Image img = new Image(3, 2);
            img.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 0);
            img.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 0);
            img.setPixel(new Pixel(0.3f, 0, 0, 0), 2, 0);
            img.setPixel(new Pixel(0.4f, 0, 0, 0), 0, 1);
            img.setPixel(new Pixel(0.5f, 0, 0, 0), 1, 1);
            img.setPixel(new Pixel(0.6f, 0, 0, 0), 2, 1);

            /*
            [0.1,0.2,0.3]
            [0.4,0.5,0.6]

            [0.3,0.2,0.1]
            [0.6,0.5,0.4]
             */
            Image imgHorizontalFlip = new Image(3, 2);
            imgHorizontalFlip.setPixel(new Pixel(0.3f, 0, 0, 0), 0, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.1f, 0, 0, 0), 2, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.6f, 0, 0, 0), 0, 1);
            imgHorizontalFlip.setPixel(new Pixel(0.5f, 0, 0, 0), 1, 1);
            imgHorizontalFlip.setPixel(new Pixel(0.4f, 0, 0, 0), 2, 1);

            assertEquals(imgHorizontalFlip, new Flip(false).apply(img));
        });
    }

    @Test
    public void test02() {
        //Vertical Flip test
        assertDoesNotThrow(() -> {
            Image img = new Image(3, 2);
            img.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 0);
            img.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 0);
            img.setPixel(new Pixel(0.3f, 0, 0, 0), 2, 0);
            img.setPixel(new Pixel(0.4f, 0, 0, 0), 0, 1);
            img.setPixel(new Pixel(0.5f, 0, 0, 0), 1, 1);
            img.setPixel(new Pixel(0.6f, 0, 0, 0), 2, 1);

            /*
            [0.1,0.2,0.3]
            [0.4,0.5,0.6]

            [0.4,0.5,0.6]
            [0.1,0.2,0.3]
             */
            Image imgHorizontalFlip = new Image(3, 2);
            imgHorizontalFlip.setPixel(new Pixel(0.4f, 0, 0, 0), 0, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.5f, 0, 0, 0), 1, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.6f, 0, 0, 0), 2, 0);
            imgHorizontalFlip.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 1);
            imgHorizontalFlip.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 1);
            imgHorizontalFlip.setPixel(new Pixel(0.3f, 0, 0, 0), 2, 1);

            assertEquals(imgHorizontalFlip, new Flip(true).apply(img));
        });
    }
}
