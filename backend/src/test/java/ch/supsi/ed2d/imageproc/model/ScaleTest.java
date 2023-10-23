package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.model.filters.Scale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScaleTest {

    @Test
    public void testApply() {
        assertDoesNotThrow(() -> {
            Image img = new Image(2, 2);
            img.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 0);
            img.setPixel(new Pixel(0.2f, 0, 0, 0), 1, 0);
            img.setPixel(new Pixel(0.3f, 0, 0, 0), 0, 1);
            img.setPixel(new Pixel(0.4f, 0, 0, 0), 1, 1);

            /*
            [0.1,0.2]
            [0.3,0.4]

            [0.1,0.1,0.2,0.2]
            [0.1,0.1,0.2,0.2]
            [0.3,0.3,0.4,0.4]
            [0.3,0.3,0.4,0.4]
             */

            Image imgResized = new Image(4, 4);
            imgResized.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 0);
            imgResized.setPixel(new Pixel(0.1f, 0, 0, 0), 1, 0);
            imgResized.setPixel(new Pixel(0.2f, 0, 0, 0), 2, 0);
            imgResized.setPixel(new Pixel(0.2f, 0, 0, 0), 3, 0);
            imgResized.setPixel(new Pixel(0.1f, 0, 0, 0), 0, 1);
            imgResized.setPixel(new Pixel(0.1f, 0, 0, 0), 1, 1);
            imgResized.setPixel(new Pixel(0.2f, 0, 0, 0), 2, 1);
            imgResized.setPixel(new Pixel(0.2f, 0, 0, 0), 3, 1);
            imgResized.setPixel(new Pixel(0.3f, 0, 0, 0), 0, 2);
            imgResized.setPixel(new Pixel(0.3f, 0, 0, 0), 1, 2);
            imgResized.setPixel(new Pixel(0.4f, 0, 0, 0), 2, 2);
            imgResized.setPixel(new Pixel(0.4f, 0, 0, 0), 3, 2);
            imgResized.setPixel(new Pixel(0.3f, 0, 0, 0), 0, 3);
            imgResized.setPixel(new Pixel(0.3f, 0, 0, 0), 1, 3);
            imgResized.setPixel(new Pixel(0.4f, 0, 0, 0), 2, 3);
            imgResized.setPixel(new Pixel(0.4f, 0, 0, 0), 3, 3);

            assertEquals(imgResized, new Scale(4,4).apply(img));
        });
    }
}
