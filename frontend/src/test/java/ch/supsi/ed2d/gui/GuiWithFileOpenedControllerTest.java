package ch.supsi.ed2d.gui;

import ch.supsi.ed2d.gui.commands.AbstractSaveFileChooserCommand;
import ch.supsi.ed2d.gui.controllers.MainViewController;
import ch.supsi.ed2d.gui.controllers.MainViewControllerTest;
import ch.supsi.ed2d.gui.models.Command;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.util.WaitForAsyncUtils.waitForAsync;


public class GuiWithFileOpenedControllerTest extends GuiControllerTest{

    protected File fileInstance;

    protected final Command<File> openFileChooserMock = () -> CompletableFuture.supplyAsync(() -> fileInstance);

    private static final int timeout = 5000;

    protected final AbstractSaveFileChooserCommand saveFileChooserMock = new AbstractSaveFileChooserCommand() {
        @Override
        public CompletableFuture<File> execute() {
            return CompletableFuture.supplyAsync(() -> fileInstance);
        }
    };



    @BeforeEach
    public void loadFile(FxRobot robot)
    {
        System.out.println("Init testUnlockEditMenu");
        ClassLoader classLoader = MainViewControllerTest.class.getClassLoader();
        fileInstance = new File(Objects.requireNonNull(classLoader.getResource("bliss_ascii.ppm")).getFile());

        assertNull(robot.lookup("#menuedit").tryQuery().orElse(null));

        robot.clickOn("File").clickOn("Open");
        assertTrue(Objects.requireNonNull(robot.lookup("#bottomBarProgressBar").tryQuery().orElse(null)).isVisible());
        assertEquals("Loading image...", ((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText());
        waitForAsync(timeout, () -> {
            while (robot.lookup("#mainImageView").tryQuery().isEmpty()) ;
            while (!((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("Successfully loaded: "))
                ;
        });
        assertTrue(((Label) Objects.requireNonNull(robot.lookup("#bottomBarLabel").tryQuery().orElse(null))).getText().contains("bliss_ascii.ppm"));
    }


}
