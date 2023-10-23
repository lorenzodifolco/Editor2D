package ch.supsi.ed2d.gui.controllers.commands;

import ch.supsi.ed2d.gui.commands.SaveCommand;
import ch.supsi.ed2d.imageproc.PNM.PNMInterpreter;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SaveCommandTest {

    @TempDir
    File tempDir;
    @Test
    void execute1() {
        assertDoesNotThrow(()->{
            Image img = new Image(4,5);
            img.apply((x,y)->img.setPixel(Pixel.rgb(255,255,255),x,y));

            String savePath = tempDir.getPath()+File.separator+"savetest.pbm";
            new SaveCommand(img,savePath, PNMInterpreter.PNMTypes.P1).execute().whenComplete((path, throwable)->{
                File f = new File(path);
                assertTrue(f.exists());
                assertTrue(f.delete());
                assertNull(throwable);
            });
        });
    }

    @Test
    void execute2() {
        assertDoesNotThrow(()->{
            String savePath = tempDir.getPath()+File.separator+"savetest.pbm";
            new SaveCommand(null,savePath, PNMInterpreter.PNMTypes.P1).execute().whenComplete((path, throwable)-> assertNotNull(throwable));
        });
    }
}