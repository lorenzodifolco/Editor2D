package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.model.filters.Rotate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RotateTest {

    @Test
    public void test01() {
        //Right rotation test
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

        [0.4,0.1]
        [0.5,0.2]
        [0.6,0.3]
         */

            Image imgRightRotated = new Image(2, 3);
            imgRightRotated.setPixel(new Pixel(0.4f, 0, 0, 0), 0, 0);
            imgRightRotated.setPixel(new Pixel(0.1f, 0, 0, 0), 1, 0);
            imgRightRotated.setPixel(new Pixel(0.5f, 0, 0, 0), 0, 1);
            imgRightRotated.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 1);
            imgRightRotated.setPixel(new Pixel(0.6f, 0, 0, 0), 0, 2);
            imgRightRotated.setPixel(new Pixel(0.3f, 0, 0, 0), 1, 2);

            assertEquals(imgRightRotated, new Rotate(true).apply(img));

            assertEquals(img.getWidth(), imgRightRotated.getHeight());
            assertEquals(img.getHeight(), imgRightRotated.getWidth());

        });
    }

    @Test
    public void test02() {
        //Left rotation test
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

        [0.3,0.6]
        [0.2,0.5]
        [0.1,0.4]
         */

            Image imgLeftRotated = new Image(2, 3);
            imgLeftRotated.setPixel(new Pixel(0.3f, 0, 0, 0), 0, 0);
            imgLeftRotated.setPixel(new Pixel(0.6f, 0, 0, 0), 1, 0);
            imgLeftRotated.setPixel(new Pixel(0.2f, 0, 0, 0), 0, 1);
            imgLeftRotated.setPixel(new Pixel(0.5f, 0, 0, 0), 1, 1);
            imgLeftRotated.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 2);
            imgLeftRotated.setPixel(new Pixel(0.4f, 0, 0, 0), 1, 2);

            assertEquals(imgLeftRotated, new Rotate(false).apply(img));

            assertEquals(img.getWidth(), imgLeftRotated.getHeight());
            assertEquals(img.getHeight(), imgLeftRotated.getWidth());

        });
    }
}
