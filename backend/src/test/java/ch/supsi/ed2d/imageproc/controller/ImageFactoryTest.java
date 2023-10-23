package ch.supsi.ed2d.imageproc.controller;

import ch.supsi.ed2d.imageproc.FileNotSupportedException;
import ch.supsi.ed2d.imageproc.ImageFactory;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.PNM.P3;
import ch.supsi.ed2d.imageproc.PNM.PNMInterpreter;
import ch.supsi.ed2d.imageproc.model.Image;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class ImageFactoryTest {

    @TempDir
    static File tempDir;

    @Test
    void addInterpreter() {
        ImageFactory.getInstance().addInterpreter(P3.getInstance(), PNMInterpreter.PNMTypes.P3);
        ClassLoader classLoader = ImageFactoryTest.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            ImageFactory.getInstance().load(file);
        });
    }

    @Test
    void getImage() {
        assertDoesNotThrow(() -> {
            ImageFactory.getInstance().addInterpreter(P3.getInstance(), PNMInterpreter.PNMTypes.P3);
            ClassLoader classLoader = ImageFactoryTest.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            Image img = ImageFactory.getInstance().load(file);
            assertEquals(300, img.getWidth());
            assertEquals(241, img.getHeight());
            assertEquals(239, img.getPixel(0, 0).getB());
        });

        assertThrows(FileNotSupportedException.class, () -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("file_with_unsupported_exception.yhj")).getFile());
            assertTrue(file.exists());
            ImageFactory.getInstance().load(file);
        });

        assertThrows(Exception.class, () -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("file_corrupted.ppm")).getFile());
            assertTrue(file.exists());
            ImageFactory.getInstance().load(file);
        });

        assertThrows(InvalidImageException.class, () -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("file_with_invalid_values.ppm")).getFile());
            assertTrue(file.exists());
            ImageFactory.getInstance().load(file);
        });
    }

    @Test
    void loadExceptions() {
        assertThrows(FileNotSupportedException.class, () -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("file_with_unsupported_exception.yhj")).getFile());
            ImageFactory.getInstance().load(file);
        });


        assertThrows(InvalidImageException.class, () -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("file_with_invalid_values.ppm")).getFile());
            ImageFactory.getInstance().load(file);
        });
    }

    @Test
    void loadP1() {
        assertDoesNotThrow(() -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_bw_ascii.pbm")).getFile());
            assertTrue(file.exists());
            Image img = ImageFactory.getInstance().load(file);
            assertEquals(300, img.getWidth());
            assertEquals(241, img.getHeight());
            assertEquals(255, img.getPixel(0, 0).getB()); // first value is 0, which is white, therefore in RGB must be 255
        });
    }

    @Test
    void loadP2() {
        assertDoesNotThrow(() -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_gs_ascii.pgm")).getFile());
            assertTrue(file.exists());
            Image img = ImageFactory.getInstance().load(file);
            assertEquals(300, img.getWidth());
            assertEquals(241, img.getHeight());
            assertEquals(157, img.getPixel(0, 0).getB());
        });
    }


    @Test
    void loadP3() {
        assertDoesNotThrow(() -> {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            assertTrue(file.exists());
            Image img = ImageFactory.getInstance().load(file);
            assertEquals(300, img.getWidth());
            assertEquals(241, img.getHeight());
            assertEquals(239, img.getPixel(0, 0).getB());
        });
    }


    @Test
    void saveExceptions() {
        File savedFile = new File(tempDir, "saved_image.ppm");
        assertThrows(IOException.class, () -> {
            if (!savedFile.exists())
                assertTrue(savedFile.createNewFile());

            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            Image img = ImageFactory.getInstance().load(file);
            ImageFactory.getInstance().save(img, tempDir.getPath() + File.separator + "saved_image.ppm", PNMInterpreter.PNMTypes.P3);
        });

        assertTrue(savedFile.delete());
    }

    @Test
    void saveP1() {
        assertDoesNotThrow(() -> {
            File savedFile = new File(tempDir, "saved_image.pbm");

            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            Image img = ImageFactory.getInstance().load(file);
            ImageFactory.getInstance().save(img, tempDir.getPath() + File.separator + "saved_image.pbm", PNMInterpreter.PNMTypes.P1);
            assertTrue(savedFile.exists());
            assertDoesNotThrow(() -> ImageFactory.getInstance().load(savedFile));
        });
    }


    @Test
    void saveP2() {
        assertDoesNotThrow(() -> {
            File savedFile = new File(tempDir, "saved_image.pgm");

            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            Image img = ImageFactory.getInstance().load(file);
            ImageFactory.getInstance().save(img, tempDir.getPath() + File.separator + "saved_image.pgm", PNMInterpreter.PNMTypes.P2);
            assertTrue(savedFile.exists());
            assertDoesNotThrow(() -> ImageFactory.getInstance().load(savedFile));
        });
    }

    @Test
    void saveP3() {
        assertDoesNotThrow(() -> {
            File savedFile = new File(tempDir, "saved_image.ppm");

            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
            Image img = ImageFactory.getInstance().load(file);
            ImageFactory.getInstance().save(img, tempDir.getPath() + File.separator + "saved_image.ppm", PNMInterpreter.PNMTypes.P3);
            assertTrue(savedFile.exists());
            assertEquals(ImageFactory.getInstance().load(savedFile), img);
        });
    }

    @AfterAll
    static void clearFiles() {
        if (tempDir.isDirectory())
            for (File file : Objects.requireNonNull(tempDir.listFiles()))
                if (!file.isDirectory())
                    assertTrue(file.delete());
    }

}