package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlurFilterTest {
    @Test
    void blurFilter() {
        Image img = new Image(3, 3);
        Image espectedResult = new Image(3, 3);

        assertDoesNotThrow(() -> {

            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 0, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 0, 1);
            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 0, 2);

            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 1, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 1, 1);
            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 1, 2);

            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 2, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 2, 1);
            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 2, 2);


            espectedResult.setPixel(new Pixel(0.178f, 0.178f, 0.178f, 0.0f), 0, 0);
            espectedResult.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 0, 1);
            espectedResult.setPixel(new Pixel(0.222f, 0.222f, 0.222f, 0.0f), 0, 2);

            espectedResult.setPixel(new Pixel(0.178f, 0.178f, 0.178f, 0.0f), 1, 0);
            espectedResult.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 1, 1);
            espectedResult.setPixel(new Pixel(0.222f, 0.222f, 0.222f, 0.0f), 1, 2);

            espectedResult.setPixel(new Pixel(0.178f, 0.178f, 0.178f, 0.0f), 2, 0);
            espectedResult.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 2, 1);
            espectedResult.setPixel(new Pixel(0.222f, 0.222f, 0.222f, 0.0f), 2, 2);

            Image result = BlurFilter.getInstance().apply(img);


            result.apply((x, y) ->
                    {
                        Pixel resultPixel=result.getPixel(x, y);
                        resultPixel.setFirstChannel(Math.round(resultPixel.getFirstChannel() * 1000.0f) / 1000.0f);
                        resultPixel.setSecondChannel(Math.round(resultPixel.getSecondChannel() * 1000.0f) / 1000.0f);
                        resultPixel.setThirdChannel(Math.round(resultPixel.getThirdChannel() * 1000.0f) / 1000.0f);
                        assertEquals(espectedResult.getPixel(x, y),resultPixel);
                    }
            );

        });
    }
}