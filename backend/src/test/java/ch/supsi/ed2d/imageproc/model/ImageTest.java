package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.FileNotSupportedException;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.InvalidStrategyException;
import ch.supsi.ed2d.imageproc.ImageFactory;
import ch.supsi.ed2d.imageproc.PNM.P3;
import ch.supsi.ed2d.imageproc.PNM.PNMInterpreter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    static Image img;

    @BeforeAll
    static void setImg() throws FileNotSupportedException, IOException, InvalidStrategyException, InvalidImageException {
        ImageFactory.getInstance().addInterpreter(P3.getInstance(), PNMInterpreter.PNMTypes.P3);
        ClassLoader classLoader = ImageTest.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
        assertTrue(file.exists());
        img = ImageFactory.getInstance().load(file);
    }

    @Test
    void setPixel() {
        assertDoesNotThrow(()->{
            img.setPixel(Pixel.rgb(1,2,3),14,13);
            var pixel = img.getPixel(14,13);
            assertEquals(pixel, Pixel.rgb(1,2,3));
        });

    }

    @Test
    void getPixel() {
        assertDoesNotThrow(()->{
            img.setPixel(Pixel.rgb(1,2,3),14,13);
            var pixel = img.getPixel(14,13);
            assertEquals(pixel, Pixel.rgb(1,2,3));
        });
    }

    @Test
    void copyConstructor() {
        assertDoesNotThrow(()->{
            Image copy = new Image(img);
            assertEquals(img.getWidth(),copy.getWidth());
            assertEquals(img.getHeight(),copy.getHeight());
            assertNotSame(img, copy);
            assertNotSame(img.getPixel(5,6), copy.getPixel(5,6));
            assertEquals(img, copy);
        });
    }

    @Test
    void iteratorTest()
    {
        assertDoesNotThrow(()->{
            int channel = 0;
            Image img = new Image(5,4);
            for(int y = 0; y<img.getHeight();y++)
                for(int x = 0; x<img.getWidth(); x++)
                    img.setPixel(Pixel.rgb(channel++,0,0),x,y);

            channel = 0;
            int total = img.getWidth()*img.getHeight();
            for (Pixel p: img) {
                assertEquals(channel++, p.getR());
            }
            assertEquals(total,channel);
        });
    }

    @Test
    void applyTest() {
        assertDoesNotThrow(() -> {
            Image img = new Image(5, 4);
            for (int y = 0; y < img.getHeight(); y++)
                for (int x = 0; x < img.getWidth(); x++)
                    img.setPixel(Pixel.rgb(x, y, x + y), x, y);
            Image img2 = new Image(5, 4);
            img2.apply((x, y) -> img2.setPixel(Pixel.rgb(x, y, x + y), x, y));
            for (int y = 0; y < img.getHeight(); y++)
                for (int x = 0; x < img.getWidth(); x++)
                    assertEquals(img.getPixel(x, y),img2.getPixel(x, y));
        });
    }

    @Test
    void imageEquals()
    {
        assertDoesNotThrow(()->{
            ClassLoader classLoader = ImageTest.class.getClassLoader();
            File file1 = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            File file2 = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            assertTrue(file1.exists());
            assertTrue(file2.exists());
            var img1 = ImageFactory.getInstance().load(file2);
            var img2 = ImageFactory.getInstance().load(file2);
            assertEquals(img1,img2);
        });

    }

    @Test
    void getWidth() {
        assertEquals(300, img.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(241, img.getHeight());
    }
}