package ch.supsi.ed2d.gui.controllers.commands;

import ch.supsi.ed2d.gui.commands.LoadCommand;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoadCommandTest {

    @Test
    void execute1() {
        var classLoader = LoadCommandTest.class.getClassLoader();
        var fileInstance = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());
        assertTrue(fileInstance.exists());
        new LoadCommand(fileInstance).execute().whenComplete((res, throwable)->{
            assertNotNull(res);
            assertNotNull(res.getImg());
            assertEquals(fileInstance.getAbsolutePath(), res.getPath());
        });
    }

    @Test
    void execute2() {
        var classLoader = LoadCommandTest.class.getClassLoader();
        var fileInstance = new File(Objects.requireNonNull(classLoader.getResource("")).getPath()+"non_existent.ppm");
        assertFalse(fileInstance.exists());
        new LoadCommand(fileInstance).execute().whenComplete((res, throwable)->assertNotNull(throwable));
    }
}