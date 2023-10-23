package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.GuiControllerTest;
import ch.supsi.ed2d.gui.commands.AbstractSaveFileChooserCommand;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SuppressWarnings("unused")
class SaveDialogControllerTest extends GuiControllerTest {


    private SaveDialogController sdc;
    private CompletableFuture<SaveDialogController.SaveDialogResult> completableFuture;

    @TempDir
    private File tempDir;

    @Init
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Start
    public void start(Stage stage)
    {
        System.out.print("creating image...");
        final Image img = new Image(4, 4);
        assertDoesNotThrow(()-> img.apply((x, y)-> img.setPixel(Pixel.rgb(1,2,4),x,y)));
        System.out.println("done");

        System.out.print("creating border pane...");
        final var bp = new BorderPane();
        System.out.println("done");

        System.out.print("creating and setting stage scene...");
        stage.setScene(new Scene(bp));
        System.out.println("done");

        System.out.print("showing stage...");
        stage.show();
        System.out.println("done");

        Controller mockController = ()->bp.getScene().getWindow();

        sdc = new SaveDialogController(mockController);
        sdc.setSaveFileChooserCommand(new AbstractSaveFileChooserCommand() {
            @Override
            public CompletableFuture<File> execute() {
                return CompletableFuture.completedFuture(null);
            }
        });
        assertDoesNotThrow(()->{
            completableFuture = sdc.loadAndShowView();
        });
    }

    @Stop
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void test01(FxRobot fxRobot) {
        System.out.print("running test 1...");
        fxRobot.clickOn("#saveButton");
        fxRobot.clickOn("OK");
        fxRobot.clickOn("Cancel");
        System.out.println("done");
    }

    @Test
    void test05(FxRobot fxRobot) {
        System.out.print("running test 5...");
        assertNotNull(sdc.getWindow());
        fxRobot.clickOn("Cancel");
    }

    @Test
    void test06(FxRobot fxRobot) throws ExecutionException, InterruptedException {
        System.out.print("running test 6...");

        System.out.print("creating command...");
        var abstractFileChooserCommand = new AbstractSaveFileChooserCommand() {
            @Override
            public CompletableFuture<File> execute() {
                return CompletableFuture.completedFuture(null);
            }
        };

        System.out.print("setting command...");
        sdc.setSaveFileChooserCommand(abstractFileChooserCommand);

        System.out.print("clicking...");
        fxRobot.clickOn("#pgmRadio");
        fxRobot.clickOn("#saveButton");

        System.out.print("joining future...");
        var res = completableFuture.join();

        System.out.print("asserting result...");
        assertNull(res);
    }

    @Test
    void test07(FxRobot fxRobot) {
        System.out.print("running test 7...");
        var abstractFileChooserCommand = new AbstractSaveFileChooserCommand() {
            @Override
            public CompletableFuture<File> execute() {
                return CompletableFuture.completedFuture(new File(tempDir.getPath(),"file_save_test"));
            }
        };

        sdc.setSaveFileChooserCommand(abstractFileChooserCommand);
        saveFile("#pgmRadio",".pgm",fxRobot);
    }

    @Test
    void test08(FxRobot fxRobot) {
        System.out.print("running test 8...");
        var abstractFileChooserCommand = new AbstractSaveFileChooserCommand() {
            @Override
            public CompletableFuture<File> execute() {
                return CompletableFuture.completedFuture(new File(tempDir.getPath(),"file_save_test"));
            }
        };

        sdc.setSaveFileChooserCommand(abstractFileChooserCommand);
        saveFile("#ppmRadio",".ppm",fxRobot);
    }

    @Test
    void test09(FxRobot fxRobot) {
        System.out.print("running test 9...");
        var abstractFileChooserCommand = new AbstractSaveFileChooserCommand() {
            @Override
            public CompletableFuture<File> execute() {
                return CompletableFuture.completedFuture(new File(tempDir.getPath(),"file_save_test"));
            }
        };

        sdc.setSaveFileChooserCommand(abstractFileChooserCommand);
        saveFile("#pbmRadio",".pbm",fxRobot);
    }

    private void saveFile(String id, String extension, FxRobot fxRobot)
    {
        fxRobot.clickOn(id);
        fxRobot.clickOn("#saveButton");
        var res = completableFuture.join();
        assertNotNull(res);
        assertNotNull(res.getPath());
        assertTrue(res.getPath().contains(extension));
        assertNotNull(res.getType());
        File f = new File(res.getPath());
        assertFalse(f.exists());
        if(f.exists())
            assertTrue(f.delete());
    }
}